# 뷰


비트맵과 메모리의 상관관계

JPEG나 PNG는 압축포맷이기 때문에 비트맵으로 디코딩될때 파일의 사이즈보다 더 많은 메모리를 사용하게 된다.

그 외에 비트맵과 메모리에 관한 내용<br>
https://medium.com/marojuns-android/%EC%95%88%EB%93%9C%EB%A1%9C%EC%9D%B4%EB%93%9C%EC%97%90%EC%84%9C%EC%9D%98-%EB%B9%84%ED%8A%B8%EB%A7%B5%EA%B3%BC-%EB%A9%94%EB%AA%A8%EB%A6%AC%EC%9D%98-%EC%83%81%EA%B4%80%EA%B4%80%EA%B3%84-125308c293b9
---
안드로이드 메모리 관리(OutOfMemory) - Bitmap
>안드로이드 3.0 이하 : Bitmap의 메모리가 Dalvik VM(달빅 가상머신)에 할당되는 것이 아니고 Native Heap영역에 할당되기 때문에 Bitmap이 VM의 GC(Garbage Collecting)의 대상이 되지 않는다. 즉, recycle()을 호출해줘야함.<br><br>
안드로이드 3.0 이상 : Bitmap의 메모리가 VM에 할당되기 때문에 다른 객체들 처럼 참조를 끊는 것이 가능하며 참조를 끊으면 GC의 대상이된다. 즉, recycle() 을 호출하지 않아도 bitmap = null; 로 메모리를 환원할 수 있다.


출처: http://ccdev.tistory.com/2 [초보코딩왕의 Power Dev.]

---

View.java의 코드이다.
자세히 보면 measure()가 호출되고 measure()안에서 onMeasure()이 호출된다.


FrameLayout.java를 확인해보면<br>
ViewGroup은 View를 상속받아 onMeasure안에서 자식들에 대해서 measure를 호출하는 형식으로 진행된다.

onMeasure은 setMeasuredDimension()이 반드시 필요하다.
따라서 오버라이드시 super 또는 호출해주어야 한다.

Layout / Draw도 같은 구조로 예상되나, 추가적으로 소스 분석이 필요하다.


	public final void measure(int widthMeasureSpec, int heightMeasureSpec) {
        boolean optical = isLayoutModeOptical(this);
        if (optical != isLayoutModeOptical(mParent)) {
            Insets insets = getOpticalInsets();
            int oWidth  = insets.left + insets.right;
            int oHeight = insets.top  + insets.bottom;
            widthMeasureSpec  = MeasureSpec.adjust(widthMeasureSpec,  optical ? -oWidth  : oWidth);
            heightMeasureSpec = MeasureSpec.adjust(heightMeasureSpec, optical ? -oHeight : oHeight);
        }

        // Suppress sign extension for the low bytes
        long key = (long) widthMeasureSpec << 32 | (long) heightMeasureSpec & 0xffffffffL;
        if (mMeasureCache == null) mMeasureCache = new LongSparseLongArray(2);

        final boolean forceLayout = (mPrivateFlags & PFLAG_FORCE_LAYOUT) == PFLAG_FORCE_LAYOUT;

        // Optimize layout by avoiding an extra EXACTLY pass when the view is
        // already measured as the correct size. In API 23 and below, this
        // extra pass is required to make LinearLayout re-distribute weight.
        final boolean specChanged = widthMeasureSpec != mOldWidthMeasureSpec
                || heightMeasureSpec != mOldHeightMeasureSpec;
        final boolean isSpecExactly = MeasureSpec.getMode(widthMeasureSpec) == MeasureSpec.EXACTLY
                && MeasureSpec.getMode(heightMeasureSpec) == MeasureSpec.EXACTLY;
        final boolean matchesSpecSize = getMeasuredWidth() == MeasureSpec.getSize(widthMeasureSpec)
                && getMeasuredHeight() == MeasureSpec.getSize(heightMeasureSpec);
        final boolean needsLayout = specChanged
                && (sAlwaysRemeasureExactly || !isSpecExactly || !matchesSpecSize);

        if (forceLayout || needsLayout) {
            // first clears the measured dimension flag
            mPrivateFlags &= ~PFLAG_MEASURED_DIMENSION_SET;

            resolveRtlPropertiesIfNeeded();

            int cacheIndex = forceLayout ? -1 : mMeasureCache.indexOfKey(key);
            if (cacheIndex < 0 || sIgnoreMeasureCache) {
                // measure ourselves, this should set the measured dimension flag back
                onMeasure(widthMeasureSpec, heightMeasureSpec);
                mPrivateFlags3 &= ~PFLAG3_MEASURE_NEEDED_BEFORE_LAYOUT;
            } else {
                long value = mMeasureCache.valueAt(cacheIndex);
                // Casting a long to int drops the high 32 bits, no mask needed
                setMeasuredDimensionRaw((int) (value >> 32), (int) value);
                mPrivateFlags3 |= PFLAG3_MEASURE_NEEDED_BEFORE_LAYOUT;
            }

            // flag not set, setMeasuredDimension() was not invoked, we raise
            // an exception to warn the developer
            if ((mPrivateFlags & PFLAG_MEASURED_DIMENSION_SET) != PFLAG_MEASURED_DIMENSION_SET) {
                throw new IllegalStateException("View with id " + getId() + ": "
                        + getClass().getName() + "#onMeasure() did not set the"
                        + " measured dimension by calling"
                        + " setMeasuredDimension()");
            }

            mPrivateFlags |= PFLAG_LAYOUT_REQUIRED;
        }

        mOldWidthMeasureSpec = widthMeasureSpec;
        mOldHeightMeasureSpec = heightMeasureSpec;

        mMeasureCache.put(key, ((long) mMeasuredWidth) << 32 |
                (long) mMeasuredHeight & 0xffffffffL); // suppress sign extension
    }

    /**
     * <p>
     * Measure the view and its content to determine the measured width and the
     * measured height. This method is invoked by {@link #measure(int, int)} and
     * should be overridden by subclasses to provide accurate and efficient
     * measurement of their contents.
     * </p>
     *
     * <p>
     * <strong>CONTRACT:</strong> When overriding this method, you
     * <em>must</em> call {@link #setMeasuredDimension(int, int)} to store the
     * measured width and height of this view. Failure to do so will trigger an
     * <code>IllegalStateException</code>, thrown by
     * {@link #measure(int, int)}. Calling the superclass'
     * {@link #onMeasure(int, int)} is a valid use.
     * </p>
     *
     * <p>
     * The base class implementation of measure defaults to the background size,
     * unless a larger size is allowed by the MeasureSpec. Subclasses should
     * override {@link #onMeasure(int, int)} to provide better measurements of
     * their content.
     * </p>
     *
     * <p>
     * If this method is overridden, it is the subclass's responsibility to make
     * sure the measured height and width are at least the view's minimum height
     * and width ({@link #getSuggestedMinimumHeight()} and
     * {@link #getSuggestedMinimumWidth()}).
     * </p>
     *
     * @param widthMeasureSpec horizontal space requirements as imposed by the parent.
     *                         The requirements are encoded with
     *                         {@link android.view.View.MeasureSpec}.
     * @param heightMeasureSpec vertical space requirements as imposed by the parent.
     *                         The requirements are encoded with
     *                         {@link android.view.View.MeasureSpec}.
     *
     * @see #getMeasuredWidth()
     * @see #getMeasuredHeight()
     * @see #setMeasuredDimension(int, int)
     * @see #getSuggestedMinimumHeight()
     * @see #getSuggestedMinimumWidth()
     * @see android.view.View.MeasureSpec#getMode(int)
     * @see android.view.View.MeasureSpec#getSize(int)
     */
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension(getDefaultSize(getSuggestedMinimumWidth(), widthMeasureSpec),
                getDefaultSize(getSuggestedMinimumHeight(), heightMeasureSpec));
    }

    /**
     * <p>This method must be called by {@link #onMeasure(int, int)} to store the
     * measured width and measured height. Failing to do so will trigger an
     * exception at measurement time.</p>
     *
     * @param measuredWidth The measured width of this view.  May be a complex
     * bit mask as defined by {@link #MEASURED_SIZE_MASK} and
     * {@link #MEASURED_STATE_TOO_SMALL}.
     * @param measuredHeight The measured height of this view.  May be a complex
     * bit mask as defined by {@link #MEASURED_SIZE_MASK} and
     * {@link #MEASURED_STATE_TOO_SMALL}.
     */
    protected final void setMeasuredDimension(int measuredWidth, int measuredHeight) {
        boolean optical = isLayoutModeOptical(this);
        if (optical != isLayoutModeOptical(mParent)) {
            Insets insets = getOpticalInsets();
            int opticalWidth  = insets.left + insets.right;
            int opticalHeight = insets.top  + insets.bottom;

            measuredWidth  += optical ? opticalWidth  : -opticalWidth;
            measuredHeight += optical ? opticalHeight : -opticalHeight;
        }
        setMeasuredDimensionRaw(measuredWidth, measuredHeight);
    }
    
    
    
onMeasure(int widthMeasureSpec, int heightMeasureSpec)에서 파라미터로 전달되는 widthMeasureSpec, heightMeasureSpec은 부모 컨테이너에서 이 뷰에게 허용하는 여유 공간의 폭과 높이에 대한 정보이다. 즉 부모 컨테이너에서 여유 공간에 대한 정보를 전달하고 이 값을 참조하여 뷰가 보일 적절한 크기를 리턴하면 이 크기값을 이용해 뷰가 그려지게 된다.

onMeasure() 메소드에서 부모 컨테이너로 크기 값을 리턴하고 싶을 때는 다음 메소드를 이용한다.
	
    void setMeasuredDimension(int measuredWidth, int measuredHeight)
    
<br>
아래는 draw에 대한 View.java에서의 구현이다.
    
	@CallSuper
    public void draw(Canvas canvas) {
        final int privateFlags = mPrivateFlags;
        final boolean dirtyOpaque = (privateFlags & PFLAG_DIRTY_MASK) == PFLAG_DIRTY_OPAQUE &&
                (mAttachInfo == null || !mAttachInfo.mIgnoreDirtyState);
        mPrivateFlags = (privateFlags & ~PFLAG_DIRTY_MASK) | PFLAG_DRAWN;

        /*
         * Draw traversal performs several drawing steps which must be executed
         * in the appropriate order:
         *
         *      1. Draw the background
         *      2. If necessary, save the canvas' layers to prepare for fading
         *      3. Draw view's content
         *      4. Draw children
         *      5. If necessary, draw the fading edges and restore layers
         *      6. Draw decorations (scrollbars for instance)
         */

        // Step 1, draw the background, if needed
        int saveCount;

        if (!dirtyOpaque) {
            drawBackground(canvas);
        }

        // skip step 2 & 5 if possible (common case)
        final int viewFlags = mViewFlags;
        boolean horizontalEdges = (viewFlags & FADING_EDGE_HORIZONTAL) != 0;
        boolean verticalEdges = (viewFlags & FADING_EDGE_VERTICAL) != 0;
        if (!verticalEdges && !horizontalEdges) {
            // Step 3, draw the content
            if (!dirtyOpaque) onDraw(canvas);

            // Step 4, draw the children
            dispatchDraw(canvas);

            // Overlay is part of the content and draws beneath Foreground
            if (mOverlay != null && !mOverlay.isEmpty()) {
                mOverlay.getOverlayView().dispatchDraw(canvas);
            }

            // Step 6, draw decorations (foreground, scrollbars)
            onDrawForeground(canvas);

            // we're done...
            return;
        }

        /*
         * Here we do the full fledged routine...
         * (this is an uncommon case where speed matters less,
         * this is why we repeat some of the tests that have been
         * done above)
         */

        boolean drawTop = false;
        boolean drawBottom = false;
        boolean drawLeft = false;
        boolean drawRight = false;

        float topFadeStrength = 0.0f;
        float bottomFadeStrength = 0.0f;
        float leftFadeStrength = 0.0f;
        float rightFadeStrength = 0.0f;

        // Step 2, save the canvas' layers
        int paddingLeft = mPaddingLeft;

        final boolean offsetRequired = isPaddingOffsetRequired();
        if (offsetRequired) {
            paddingLeft += getLeftPaddingOffset();
        }

        int left = mScrollX + paddingLeft;
        int right = left + mRight - mLeft - mPaddingRight - paddingLeft;
        int top = mScrollY + getFadeTop(offsetRequired);
        int bottom = top + getFadeHeight(offsetRequired);

        if (offsetRequired) {
            right += getRightPaddingOffset();
            bottom += getBottomPaddingOffset();
        }

        final ScrollabilityCache scrollabilityCache = mScrollCache;
        final float fadeHeight = scrollabilityCache.fadingEdgeLength;
        int length = (int) fadeHeight;

        // clip the fade length if top and bottom fades overlap
        // overlapping fades produce odd-looking artifacts
        if (verticalEdges && (top + length > bottom - length)) {
            length = (bottom - top) / 2;
        }

        // also clip horizontal fades if necessary
        if (horizontalEdges && (left + length > right - length)) {
            length = (right - left) / 2;
        }

        if (verticalEdges) {
            topFadeStrength = Math.max(0.0f, Math.min(1.0f, getTopFadingEdgeStrength()));
            drawTop = topFadeStrength * fadeHeight > 1.0f;
            bottomFadeStrength = Math.max(0.0f, Math.min(1.0f, getBottomFadingEdgeStrength()));
            drawBottom = bottomFadeStrength * fadeHeight > 1.0f;
        }

        if (horizontalEdges) {
            leftFadeStrength = Math.max(0.0f, Math.min(1.0f, getLeftFadingEdgeStrength()));
            drawLeft = leftFadeStrength * fadeHeight > 1.0f;
            rightFadeStrength = Math.max(0.0f, Math.min(1.0f, getRightFadingEdgeStrength()));
            drawRight = rightFadeStrength * fadeHeight > 1.0f;
        }

        saveCount = canvas.getSaveCount();

        int solidColor = getSolidColor();
        if (solidColor == 0) {
            final int flags = Canvas.HAS_ALPHA_LAYER_SAVE_FLAG;

            if (drawTop) {
                canvas.saveLayer(left, top, right, top + length, null, flags);
            }

            if (drawBottom) {
                canvas.saveLayer(left, bottom - length, right, bottom, null, flags);
            }

            if (drawLeft) {
                canvas.saveLayer(left, top, left + length, bottom, null, flags);
            }

            if (drawRight) {
                canvas.saveLayer(right - length, top, right, bottom, null, flags);
            }
        } else {
            scrollabilityCache.setFadeColor(solidColor);
        }

        // Step 3, draw the content
        if (!dirtyOpaque) onDraw(canvas);

        // Step 4, draw the children
        dispatchDraw(canvas);

        // Step 5, draw the fade effect and restore layers
        final Paint p = scrollabilityCache.paint;
        final Matrix matrix = scrollabilityCache.matrix;
        final Shader fade = scrollabilityCache.shader;

        if (drawTop) {
            matrix.setScale(1, fadeHeight * topFadeStrength);
            matrix.postTranslate(left, top);
            fade.setLocalMatrix(matrix);
            p.setShader(fade);
            canvas.drawRect(left, top, right, top + length, p);
        }

        if (drawBottom) {
            matrix.setScale(1, fadeHeight * bottomFadeStrength);
            matrix.postRotate(180);
            matrix.postTranslate(left, bottom);
            fade.setLocalMatrix(matrix);
            p.setShader(fade);
            canvas.drawRect(left, bottom - length, right, bottom, p);
        }

        if (drawLeft) {
            matrix.setScale(1, fadeHeight * leftFadeStrength);
            matrix.postRotate(-90);
            matrix.postTranslate(left, top);
            fade.setLocalMatrix(matrix);
            p.setShader(fade);
            canvas.drawRect(left, top, left + length, bottom, p);
        }

        if (drawRight) {
            matrix.setScale(1, fadeHeight * rightFadeStrength);
            matrix.postRotate(90);
            matrix.postTranslate(right, top);
            fade.setLocalMatrix(matrix);
            p.setShader(fade);
            canvas.drawRect(right - length, top, right, bottom, p);
        }

        canvas.restoreToCount(saveCount);

        // Overlay is part of the content and draws beneath Foreground
        if (mOverlay != null && !mOverlay.isEmpty()) {
            mOverlay.getOverlayView().dispatchDraw(canvas);
        }

        // Step 6, draw decorations (foreground, scrollbars)
        onDrawForeground(canvas);
    }
    
-------
    
# 스레드
-----

안드로이드는 백그라운드에서 실행되어야 하는 기능을 서비스라는 애플리케이션 구성요소로 정의한다.

그러나 별도의 프로세스가 아닌 동일한 프로세스 내에서 위의 문제를 해결하려면 하나의 프로세스 안에서 여러 개의 작업이 동시 수행되는 멀티 스레드 방식을 선택할 수도 있다.

스레드(Thread)는 동시 수행이 가능한 작업 단위이며, 현재 수행되는 작업 이외의 기능을 동시에 처리하고자 할 때 새로운 스레드를 만들어 처리할 수 있다. 이러한 멀티 스레드 방식은 같은 프로세스 안에 들어 있으면서 메모리 리소르를 공유하게 되므로 효율적인 처리가 가능하지만 동시에 리소스를 접근할 경우에 데드락(Dead lock)이 발샐하여 시스템이 비정상적으로 동작할 수 있음을 알아두어야 한다.


서비스 : 백그라운드 작업은 서비스로 실행하고 사용자에게는 알림 서비스를 이용해 알려준다. 만약 메인 액티비티로 결과 값을 전달하고 이를 이용해 다른 작업을 수행하고자 한다면 브로드캐스팅을 이용해 결과값을 전달할 수 있다.

스레드 : 스레드는 동일 프로세스 내에 있기 때문에 작업 수행의 결과를 바로 처리할 수 있다. 그러나 UI 객체는 직접 접근할 수 없으므로 핸들러(Handler) 객체를 사용한다.

***동시 접근에 따른 데드락 문제를 해결하는 가장 간단한 방법은 작업을 순서대로 처리하는 것이다. <br>이러한 역할은 메인 스레드의 핸들러가 담당하게 된다.***


스레드는 new  연산자를 이용하여 객체를 생성한 후 start() 메소드를 호출하면 시작할 수 있다.<br>
Thread 클래스에 정의된 생성자는 크게 파라미터가 없는 경우와 Runnable 객체를 파라미터로 가지는 두 가지로 구분할 수 있다. 일반적으로 Thread 클래스를 상속한 새로운 클래스를 정의한 후 객체를 만들어 시작하는 방법을 사용한다.

### 메시지를 전송하여 실행하기

앱을 실행할 때 프로세스가 만들어지면 그 안에 메인 스레드가 함께 만들어지고 최상위에서 관리되는 애플리케이션 구성 요소인 액티비티, 브로드캐스트 리시버 등과 새로 만들어지는 윈도우를 관리하기 위한 메시지큐(Message Queue)를 실행하게 된다. 이 메시지 큐를 이용해 순차적으로 코드를 수행할 수 있으며, 이렇게 메시지 큐를 이용해 메인 스레드에서 처리할 메시지를 전달하는 역할을 담당하는 것이 핸들러 클래스이다. 핸들러는 실행하기를 원하는 특정 기능이 있을 때 핸들러가 포함되어 있는 스레드에서 순차적으로 실행시킬 때 사용하게 된다. 핸들러를 이용하면 특정 메시지가 미래의 어떤 시점에 실행되도록 스케줄링 할 수도 있다.


<img src="http://postfiles10.naver.net/20110808_201/huewu_1312812058792H1Fxx_PNG/handler.png?type=w3">

새로 만든 스레드가 수행하려는 정보를 메인 스레드로 전달하기 위해서는 먼저 핸들러가 관리하는 메시지 큐에서 처리할 수 있는 메시지 객체 하나를 참조해야 한다. 이 첫 번째 과정에는 obtainMessage() 메소드를 이용할 수 있으며 호출의 결과로 메시지 객체를 리턴받는다. 이 메시지 객체에 필요한 정보를 넣은 후 sendMessage() 메소드를 이용해 메시지 큐에 넣을 수 있다. 메시지 큐에 들어간 메시지는 순서대로 핸들러가 처리하게 되며 이때 handlerMessage() 메소드에 정의된 기능이 수행된다.

***_handlerMessage()에 들어있는 코드가 수행되는 위치는 새로 만든 스레드가 아닌 메인 스레드가 된다._***



>애플리케이션을 실행하게 되면 시스템은 메인 스레드를 만든다. 이 스레드는 이벤트를 적절한 사용자 인터페이스 위젯에 디스패치하는 역할을 한다. 이 스레드는 안드로이드 UI 툴킷(android.widget과 android.view 패키지)과 상호작용을 담당하기도 한다. 따라서 이 Thread는 UI Thread라고 불리기도 한다.<br><br>
시스템은 컴포넌트를 위해 별도로 Thread를 만들어도 돌리지는 않는다. 동일한 프로세스에서 돌아가는 모든 컴포넌트는 한 개의 UI Thread에서 객체화된다. 이는 시스템의 콜백 메소드들은 하나의 Thread에서 돌아간다는 걸 의미하기도 한다.<br><br>
화면에서 집중적으로 어떤 작업을 수행해야 하는 경우 단일 Thread 모델로는 성능이 떨어질 수 있다. 특히 UI Thread에서 모든 작업이 수행될 경우 네트워크 접속이나 데이터베이서 쿼리 같이 오래 걸리는 작업을 수행하는 경우 전체 UI가 Block된다. UI Thread가 Block되면 이벤트를 디스패치할 수도 없다. 사용자 이용 측면에서 보면 애플리케이션이 행에 걸리게 된다.<br><br>
안드로이드 UI 툴킷은 Thread-safty하지 않다. 따라서 UI 프레임워크는 별도의 워커 스레드를 통해 UI를 조작하지 않아야 한다. 반드시 UI Thread를 이용해 UI를 조작해야 한다.
<br><br>나홀로 개발자를 안드로이드 프로그래밍의 모든 것 / 김지훈, 이지훈, 이현우, 김도균 저 408p)

안드로이드 Single Thread 모델에서는 다음 두 규칙을 반드시 지켜야 한다.
1. UI Thread를 차단하지 말 것.
2. 외부의 UI Thread에서 안드로이드 UI 프레임워크에 접근하지 말 것.

2번 문제를 해결하기 위해 안드로이드에서 다른 Thread에서 UI Thread로 접근할 수 있는 몇가지 방법을 제공한다.

	Activity.runOnUiThread(Runnable)
    View.post(Runnable)
    View.postDelayed(Runnable, long)

출처 : http://dayglo.tistory.com/70
    


메세지큐, 루퍼 그리고 핸들러
http://blog.naver.com/PostView.nhn?blogId=huewu&logNo=110115454542

extends Thread와 implement Runnable의 차이에 대하여

>Yes: implements Runnable is the preferred way to do it, IMO. You're not really specialising the thread's behaviour. You're just giving it something to run. That means composition is the philosophically "purer" way to go.<br><br>
In practical terms, it means you can implement Runnable and extend from another class as well.



-----
## 깨알 같이 알아가는 
JAVA <br>http://finewoo.tistory.com/48

커스텀뷰의 크기(기하 혹은 좌표계) 정하기 <br>https://skyfe79.gitbooks.io/android-custom-view-programming/content/chapter01.html

setLayout<br>http://secuinfo.tistory.com/entry/Android-Develop-setLayoutParams-getLayoutParams

extends와 implements의 차이<br>
http://gdthink.blogspot.kr/2006/06/extends%EC%99%80-implements%EC%9D%98-%EC%B0%A8%EC%9D%B4.html
