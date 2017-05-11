# 그래픽
-----

http://blog.burt.pe.kr/%EC%95%88%EB%93%9C%EB%A1%9C%EC%9D%B4%EB%93%9C-%EC%BB%A4%EC%8A%A4%ED%85%80%EB%B7%B0-%EC%9D%B4%ED%95%B4%ED%95%98%EA%B8%B0/


http://i5on9i.blogspot.kr/2013/05/android-view-onmeasure-onlayout.html


http://namsieon.com/339
-----



#### 안드로이드에서 뷰가 그려지는 과정
https://developer.android.com/guide/topics/ui/how-android-draws.html<br>
http://namsieon.com/339

> Drawing begins with the root node of the layout. It is requested to measure and draw the layout tree. Drawing is handled by walking the tree and rendering each View that intersects the invalid region. In turn, each ViewGroup is responsible for requesting each of its children to be drawn (with the draw() method) and each View is responsible for drawing itself. Because the tree is traversed in-order, this means that parents will be drawn before (i.e., behind) their children, with siblings drawn in the order they appear in the tree.

	void measure (int widthMeasureSpec, int heightMeasureSpec)
		// This is called to find out how big a view should be. The parent supplies constraint information in the width and height parameters.

	void layout (int l, int t, int r, int b)
		// Assign a size and position to a view and all of its descendants
        
       




**measure -> layout -> draw** 의 순서로!


------
#### 뷰의 주기
http://ndquangr.blogspot.kr/2013/04/android-view-lifecycle.html


	void onSizeChanged (int w, int h, int oldw, int oldh)
		// This is called during layout when the size of this view has changed. If you were just added to the view hierarchy, you're called with the old values of 0.

Android doesn't know the real size at start, it needs to calculate it. Once it's done, onSizeChanged() will notify you with the real size.

onSizeChanged() is called once the size as been calculated. Events don't have to come from users all the time. In this example when android change the size, onSizeChanged() is called. Same thing with onDraw(), when the view should bedrawn onDraw() is called.

onMeasure() is called automatically right after a call to measure()

-------
#### onDraw() / Draw()

There is difference between them

The **_onDraw(Canvas c)_** is a override method and automatically called when the view is being rendered. Here you can do your additional drawing like make circles, lines or whatever you want.

The **_draw(Canvas c)_** is used to manually render this view (and all of its children) to the given canvas. The view must have already done a full layout before this function is called. When implementing a view, implement onDraw(android.graphics.Canvas) instead of overriding this method. If you do need to override this method, call the superclass version.

Or in simple words draw(Canvas c) is simply a function that is not automatically called when the view is rendered. User is needed to provide the canvas on which this view will rendered and user also have to do all the drawing on the canvas before calling this function.

>draw는 일반적으로 View를 Canvas에 그리는데 필요한 절차가 들어있는 Method입니다. draw는 항상 View를 사용하는 곳에서 기대되는 일관적인 행동을 해야하기 때문에 override하면 안되는 method입니다. onDraw는 실제도 해당 View를 그릴때 override를 해서 사용할 수 있는 메서드구요. 
보통 외부에서 사용할때는 draw를 호출해서 canvas에 그리게 할수 있습니다. 그리고 onDraw는 draw의 과정중에 자동으로 불리는 부분이구요. protected 이니 당연히 외부에서 불릴수는 없고 자체적으로 View가 그려질때만 사용되고 다른 곳에서 호출될일은 거의 없습니다. 
draw를 어디서 어떻게 사용하는지는 View마다 사용하는데마다 천차만별이라 일률적으로 설명해드릴수 없는 부분입니다.

>onDraw는 뷰 내부에서 그려져야 하는 상황에 호출되어(콜백) Canvas가 제공되는 것
draw는 직접 호출로  Canvas를 제공해서 현재 뷰를 그려내는 것
전혀 다른 성격의 메소드

>draw = 그려 / onDraw = 그려질 때


## 그래픽 그리기
가장 쉬운 방법은 뷰 클래스를 상속한 후 이 뷰에 직접 그래픽을 그리는 것이다
1. 새로운 클래스를 만들고 뷰를 상속받는다.
2. 페인트 객체를 초기화하고 필요한 속성을 설정한다.
3. onDraw() 메소드 내에 사격형을 그리는 메소드를 호출한다.
4. onTouchEvent() 메소드 내에 터치 이벤트를 처리하는 코드를 넣는다.
5. 새로 만든 뷰를 메인 액티비티에 추가한다.


그래픽 그리기 주요 클래스

| 속성 | 설명|
|----|----|
| Canvas | 뷰의 표면에 직접 그릴 수 있도록 만들어주는 객체로 그래픽 그리기를 위한 메소드가 정의되어 있다. |
| Paint | 그래픽 그리기를 위해 필요한 색상 당의 속성을 담고 있다.|
|Bitmap | 픽셀로 구성된 이미지로 메모리에 그래픽을 그리는데 사용|
|Drawable| 사각형, 이미지 등의 그래픽 요소가 객체로 정의되어 있다.|


paint.setStyle(Style.FILL) - 채우기
paint.setStyle(Style.STROKE) - 테두리

setStrokeWidth() - 선의 두께 
 setARGB() - 투명도 설정
 DashPathEffect - 점선 효과
 
 drawLine() - 선
 drawCircle() - 원
 
 
 패스(Path) - 사각형 원 등으로 표현할 수 있는 모든 것과 더불어 다른 그리기 방법으로는 표현할 수 없는 복잡하고 다양한 곡선 및 도형을 그릴 수 있도록 한다. 클리핑(clipping)은 그리기 연산이 일어나는 영역을 설정하는 것으로 clipRect() 또는 clipRegion() 메소드를 사용하여 영역을 설정할 수 있다.
 
 
 | 속성 | 설명 |
 |--|--|
 | 점 | void drawPoint(float x, float y, Paint paint) 하나의 점을 그린다. drawPoints()를 이용하면 여러 개의 선을 그릴 수 있다.
 | 선 | void drawLine(float startX, float startY, float stopX, float stopY, Paint point)두 점의 x, y좌표값으로 선을 그린다 drawLines()를 이용하면 여러 개의 선을 그릴 수 있다.|
 |사각형 패| |
 
 ## 비트맵 이미지
 
 메모리에 만들어지는 이미지는 비트맵(Bitmap) 객체로 관리된다. 비트맵 객체로 이미지를 그릴 수 있다. 이미지 객체가 메모리에 만들어지면 이를 이용해 다양한 조작을 할 수 있으며, 이 이미지는 사진 이미지 이외에도 그래픽을 그릴 수 있는 공간을 제공한다. 즉, 흔히 더블버퍼링(Double Buffering)이라 불리는 방법을 사용하면 별도의 공간에 미리 그래픽을 그린 후 뷰가 다시 그려져야 할 필요가 있을 때 미리 그려놓은 비트맵을 화면에 표시하게 된다.
 
 
 
이미지뷰 위젯을 사용하면 손쉽게 파일을 보여줄 수 있는 장점이 있다. 하지만 뷰 위에 사진과 함께 선이나 원과 같은 그래픽을 함께 보여주기 위해서는 앞에서 살펴보았던 대로 비트맵 객체를 메모리에 만든 후 캔버스 객체를 이용해 그 위에 그려주는 것이 필요하다.

BitmapFactory 클래스는 비트맵 이미지를 만들기 위한 클래스 메소드들을 제공하며 이 메소드들은 이미지를 메모리 상의 비트맵 객체로 만들어 주기 위한 방법을 제공한다.

| 속성 | 설명 |
| --- | --- |
| 파일에서 읽기 | path를 지정하면 해당 위치의 이미지 파일을 읽어온다. <br> public static Bitmap decodeFile(String pathName) |
| 리소스에서 읽기 | 리소스에서 지정한 이미지 파일을 읽어온다. <br>public static Bitmap decodeResource(Resources res, int id) |
| 바이트 배열에서 읽기 |바이트 배열로 되어 있는 이미지 파일을 읽어온다. <br> public static Bitmap decodeByteArray(byte[] data, int offset, int length) |
| 스트림에서 읽기 | 입력 스트림에서 이미지 파일을 읽어온다. <br> public static Bitmap decodeStream(InputStream is) |

바이트 배열이나 스트림에서 읽을 수 있다는 것은 원격지에 있는 이미지 파일도 손쉽게 읽어 들일 수 있다는 것을 의미한다. 예로 원격지의 소켓 서버나 웹 서버에 있는 이미지 파일은 서버에 연결한 후 입력 스트림으로 이미지를 읽어 들이거나 바이트 배열의 형태로 읽어 들인 데이터를 decodeByteArray() 메소드를 이용해서 이미지로 변환할 수 있다.


이미지를 다른 형태로 바꾸는 과정을 이미지 변환(Transformation)이라 하는데 안드로이드에서는 이미지 변환을 위해 Matrix 클래스가 사용된다. 기본적으로 제공하는 Matrix 객체를 이용하면 확대/축소, 이동, 회전, 뒤틀림 등의 효과를 간단하게 처리할 수 있다. Matrix 객체에 정의된 메소드는 각각 setScale(), setTranslate(), setRotate(), setSkew()이다.

이미지에 다양한 효과를 내는 데는 마스크(Mask)를 사용하기도 한다. BlurMaskFilter는 번짐 효과를 낼 수 있는 마스크 필터로 페인트 객체에 설정하여 사용할 수 있다. 비트맵 이미지를 확대할 때는 createScaledBitmap() 메소드를 사용할 수 있다.



## 서피스뷰 사용하기
안드로이드는 3D 지원을 위해 OpenGL ES 라이브러리를 포함하고 있으며 앱에서 3D 객체를 보여주려면 '서피스뷰(Surface View)'를 이용해야 한다.
서피스뷰를 사용하려면 하드웨어 가속이 가능하므로 그래픽 처리가 빠르다는 장점이 있지만 이 객체는 안드로이드의 기본 뷰 체계와는 별도로 구현되었다는 점에 주의해야 한다.

뷰를 상속하여 그래픽을 그릴 때 사용하던 방법을 그대로 사용할 수 없으며 서피스뷰를 이용한 그래픽 그리기 방법을 이해해야 한다.

서피스뷰는 일반 그래픽에도 지원할 할 수 있다. 젤리빈 이후부터 일반 뷰에도 서피스뷰에서 사용하던 하드웨어 가속 기능을 사용할 수 있게 되었지만 아직도 일부 기능에는 서피스뷰가 사용된다.

서피스뷰는 안드로이드의 기본 뷰 체계에 들어 있지 않으므로, 다음과 같은 세 가지 특징을 갖는다.

| 속성 | 설명 |
| --- | --- |
| 서피스홀더(SurfaceHolder)에서 제어 | 서피스뷰를 위해 필요한 제어 기능은 서피스홀더가 갖는다.|
| 콜백 인터페이스의 구현 | 안드로이드의 기본 뷰 체계와는 별도로 구현되면서 서피스뷰의 크기 변화 등 상태 정보는 콜백 인터페이스를 위해 관리된다. 따라서 콜백 인터페이스에서는 서피스뷰의 생성, 변경 그리고 해제 메소드가 정의되어 있으며, 이 메소드들은 상태의 변화에 따라 자동으로 호출된다.|
| 잠금(Lock) 기능의 사용 | 애플리케이션에서 서피스뷰에 그래픽을 그리거나 할 때는 시스템이나 다른 애플리케이션에서 접근하는 것을 막기 위해 잠금 기능을 사용한다. 예로 서피스뷰의 크기 변경과 동시에 그래픽을 그릴 경우 데드락이 발생할 수 있으며, 이를 방지하기 위해 lockCanvas()와 unlockAndPost() 메소드 사이에서 그래픽을 그리는 작업을 수행해야한다.|


서피스뷰 사용패턴
SurfaceView 클래스를 상속하는 클래스를 새로 정의하고 이 때 Callback 인터페이스를 같이 구현한다. Callback 인터페이스를 구현하는 별도의 클래스를 정의한 후에 사용할 수도 있지만 앞서 살펴본 코드처럼 새로운 서피스뷰 클래스를 정의하는 과정에서 콜백 인터페이스를 같이 구현하면 코드 양이 적어지는 장점이 있다.

서피스뷰는 단순히 화면에 보이는 하나의 뷰를 정의한 것이고 이 서피스뷰를 내부적으로 제어하는 것은 서피스홀더 객체이므로 이 객체에 정의된 여러 가지 메소드를 필요에 따라 호출하기 위해서는 먼저 getHolder() 메소드를 호출하여 홀더 객체를 참조해야 한다. 콜백 인터페이스는 홀더 객체에 설정하도록 되어 있으므로 addCallback() 메소드를 이용해 설정하면, 콜백 인터페이스에 정의된 세 가지 메소드는 서피스뷰의 상태에 따라 자동 호출되게 된다.

서피스뷰는 뷰에서 사용되던 onDraw()와 invalidate()를 사용할 수 없다. 따라서 사용하고자 한다면 직접 구현해야 한다.

---------
## 깨알 같이 새롭게 배우는 것들

@Annotation<br>
http://www.nextree.co.kr/p5864/

-----
grid view의 속성<br>
http://stackoverflow.com/questions/5690144/how-can-i-force-a-gridview-to-use-the-whole-screen-regardless-of-display-size<br>

=> GridView.Layout.LayoutParams()에 대해서 설정하다가 생긴 의문점

        GridView.LayoutParams params = new GridView.LayoutParams(
                GridView.LayoutParams.MATCH_PARENT,
                GridView.LayoutParams.MATCH_PARENT);
                
        Button item = new Button(mContext);
        item.setText("");
        item.setLayoutParams(params);

아이템에 대해서 setLayoutParams로 값을 할당하면서 item에게 match_parent를 주어서 하나의 아이템이 부모인 grid view를 모두 차지하지 않을까라고 생각했었다. 그거에 대한 답변은 stack overflow에서..

>The point of GridView is to have "un-used white space at the bottom of the display", if you do not have enough data to fill the screen, so that it can flexibly handle multiple screen sizes and also accommodate scrolling if there is more data. If you are aiming for something akin to the dashboard pattern, consider using DashboardLayout or something along those lines.
>
----
JAVA static 변수

http://finewoo.tistory.com/48

>클래스의 변수나 메소드를 static으로 선언하면 그 변수는 객체의 변수가 아니라 클래스 변수가 된다. 클래스 변수이므로 어떠한 객체라도 동일한 주소로 참조하게 된다.
일반적으로 클래스의 변수나 메소드는 해당 클래스가 인스턴스화 되기 전에는 사용할 수 없는데, static 으로 선언된 변수나 메소드는 해당 클래스의 인스턴스 여부와 상관없이 바로 접근이 가능하고, 사용이 가능하다.



static의 의미를 제대로 알지 못해서 헤맨 코드

	if(ColorSelectDialog.listener != null){
		ColorSelectDialog.listener.onColorSelected(((Integer)v.getTag()).intValue());
	}
    

아래 예제를 보자
<br>

ColorSelectDialog에서 선언된 static 변수

	public static OnColorSelectedListener listener;
    
이 부분과 MainActivity에서 버튼 클릭시 Dialog 띄우고.. 해당 Dialog에서의 액션에 대한 listener를 등록하는 과정
**ColorPaletteDialog.listener = new OnColorSelectedListener()** 이 부분을 주목하자.

    colorBtn.setOnClickListener(new OnClickListener() {
        	public void onClick(View v) {
        		ColorPaletteDialog.listener = new OnColorSelectedListener() {
        			public void onColorSelected(int color) {
        				mColor = color;
        				board.updatePaintProperty(mColor, mSize);
        			}
        		};
        		
        		
        		// show color palette dialog
        		Intent intent = new Intent(getApplicationContext(), ColorPaletteDialog.class);
        		startActivity(intent);
        		
        	}
        });
        
        
아래는 인터페이스이다.
        
	public interface OnColorSelectedListener {
    	public void onColorSelected(int color);
    }

