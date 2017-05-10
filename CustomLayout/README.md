# CustomLayout

Android - CustomView

Try to implement LinearLayout by extends ViewGroup
Can use custom AttributeSet 'WeightSum' and 'layout_weight' in xml

Example XML code

  <com.example.naver.customlayout.CustomLayout
      xmlns:custom="http://schemas.android.com/apk/res-auto"
      xmlns:android="http://schemas.android.com/apk/res/android"
      android:layout_width="match_parent"
      android:layout_height="match_parent"
      >
      <Button
          custom:layout_weight="0.5"
          android:text="ok"
          android:layout_width="wrap_content"
          android:layout_height="wrap_content"
          />
      <Button
          custom:layout_weight="0.3"
          android:text="cancle"
          android:layout_width="match_parent"
          android:layout_height="wrap_content" />
  </com.example.naver.customlayout.CustomLayout>
