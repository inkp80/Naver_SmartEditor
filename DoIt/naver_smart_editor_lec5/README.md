# 커스텀 뷰
---
<a href="https://developer.android.com/guide/components/intents-filters.html?hl=ko">인텐트 및 인텐트 필터</a>


## 나인패치 
이미지가 늘어나거나 줄어들 때 생기는 이미지 왜곡을 해결하는 방법을 정의한 것
이미지 뷰를 XML레이아웃에 추가해서 화면에 보여줄 때 이미지 원본이 이미지가 나타나는 영역보다 작다면 시스템이 이미지 크기를 자동으로 늘려준다. 

해상도가 다른 단말에서도 일정한 비율로 크기를 지정하면 자동으로 그 크기에 맞게 늘어나거나 줄어들 수 있도록 한다.</br>
그러나 그 과정에서 이미지의 일부분이 깨져 보이거나 왜곡이 발생할 수 있기 때문에 이런 문제를 해결할 방법이 필요하다.

파일 확장자 앞에 '.9'를 붙여서 나인 패치 이미지를 만든다.

흰색인 경우 늘어나지 않는영역, 검은색인 경우 늘어나는 영역으로 구분되는



비트맵은 안드로이드에서 메모리로 로딩한 이미지를 부를 때 사용하므로 메모리 상에 만들어진 이미지라고 생각하면 된다. 이 비트맵 이미지를 이용해 버튼의 상태를 표시하려면 버튼이 눌렸을 때와 뗴어졌을 떄를 이벤트로 구분하여 처리해야 하며 여러 앱에서 재사용하기 위해서는 버튼 클래스로부터 상속받아 새로운 클래스로 정의해야 한다.

뷰를 상속하여 새로운 뷰를 만들 떄는 뷰가 그려지는 방법에 대해 이해하는 것이 중요한데 뷰는 그 영역과 크기가 부모 레이아웃 등의 영향을 받아 정해지며 레이아웃에 맞게 화면에 표현된다.뷰가 스스로의 크기를 정할 때 호출되는 메소드는 onMeasure()이고 스스로를 레이아웃에 맞게 그릴 떄는 onDraw() 메소드가 호출된다.

	public void onMeasure(int widthMeasureSpec, int heightMeasureSpac)
	public void onDraw(Canvas canvas)
    
onMeasure() 메소드의 파라미터로 전달되는 widthMeasureSpec과 heightMeasureSpec은 부모 컨테이너에서 이 뷰에게 허용하는 여유 공간의 폭과 넢이에 대한 정보이다. 즉, 부모 컨테이너에서 여유 공간에 대한 정보를 전달하고 이 값을 참조하여 뷰가 보일 적절한 크기를 리턴하면 이 크기값을 이용해 뷰가 그려진다.
onMeasure()에서 부모 컨테이너로 크기값을 리턴하고 싶을 때는 다음 메소드가 사용된다.
 
 	void setMeasureDimension(int measureWidth, int measuredHeight)

이 메소드의 두 파라미터는 뷰의 폭과 높이값이 된다.

뷰가 화면 상에 보일 때는 onDraw() 메소드가 호출된다. 예를 들어, 버튼의 경우 그림으로 된 아이콘이나 글자가 그 위에 표시되기 위해서는 레이아웃에 따라 버튼의 위치와 크기가 정해진 후 버튼의 모양과 그 안의 아이콘 또는 글자를 화면상에 그려주는 과정을 거치게 된다. 이렇게 그려지는 과정에서 호출되는 onDraw() 메소드를 다시 정의하면 보이고자 하는 내용물을 버튼 위에 그릴 수 있다.

새로운 뷰를 클래스로 정의하고 그 안에 onDraw() 메소드를 다시 정의한 후 필요한 코드를 넣어 기능을 구현하면 새로운 뷰를 직접 만들 수 있다.


버튼의 기능을 그대로 유지한 채 어떤 기능을 추가하고 싶을 때 버튼을 상속하여 새로운 위젯을 만들 수 있다.
버튼을 상속할 때는 Button 클래스가 아니라 AppCompatButton 클래스를 사용한다. 이 클래스는 예전 버전의 안드로이드에서도 사용할 수 있는 버튼이다. 새로 정의한 BitmapButton 클래스의 생성자는 두 개이다. 하나는 소스 코드에서 객체를 생성할 때 호출되며, 다른 하나는 XML 레이아웃에 추가한 경우 자동으로 메모리에 객체화되면서 호출된다.

	public BitmapButton(Context context)
    public BitmapButton(COntext context, AttributeSet atts)
    
객체를 초기화하는 기능은 init() 메소드를 새로 정의하고 그 안에 넣는다. 그러면 두 개의 생성자 모두에서 이 메소드를 호출하여 초기화할 수 있다. init() 메소드 안에서는 배경 이미지와 폰트 크기, 색상, 글꼴을 설정한다. 배경 이미지는 setBackgroundResource() 로 설정할 수 있다.

기존 위젯을 상속하여 새로운 위젯을 정의할 때 onDraw() 메소드를 재정의할 수 있다. onDraw() 메소드는 위젯이 그려질 때 호출되어 직접 그래픽으로 그릴 수 있는 기회를 제공한다.


## 리스트뷰
리스트뷰처럼 여러 개의 아이템 중에 하나를 선택할 수 있는 위젯들을 특별히 '선택 위젯(Selection Widget)'이라고 부른다.
특별히 구분하여 부르는 이유는 이 위젯들이 사용되는 방식이 다르기 때문이다.

선택 위젯은 어댑터(Apdater) 패턴을 사용하며 어댑터가 중요한 기능을 담당한다. 데이터를 위젯이 아닌 어댑터에 설정해야 하며 화면에 보이는 뷰도 어댑터에서 만든다. 리스트 뷰에 보이는 각각의 아이템은 결국 어댑터에서 관리하는 데이터와 뷰이다.

=> 원본 데이터를 위젯에 직접 설정하지 않고 어댑터라는 클래스를 사용하도록 되어 있다.

선택할 수 있는 여러 개의 아이템이 표시되는 선택 위젯은 어댑터(Adapter)를 통해 각각의 아이템을 화면에 디스플레이 한다.
따라서 원본 데이터는 어댑터에 설정해야 하며 어댑터가 데이터 관리 기능을 담당한다. 선택 위제셍 보이는 각각의 아이템이 화면에 디스플레이되기 전에 어댑터의 getView()  메소드가 호출된다. 이 메소드는 어댑터에서 가장 중요한 메소드로 이 메소드에서 리턴하는 뷰가 하나의 아이템으로 디스플레이 된다.

하나의 아이템에 여러 정보를 담아 리스트뷰로 보여줄 때 해야 하는 일들
| 속성 | 설명 |
| --- | --- |
|아이템을 위한 XML 레이아웃 정의 | 리스트뷰에 들어갈 아이템의 레이아웃을 XML로 정의|
|아이템을 위한 뷰 정의|리스트뷰에 들어갈 각 아이템을 하나의 뷰로 정의 (뷰그룹)|
|어댑터 정의|데이터 관리 역할을 하는 어댑터 클래스를 만들고 그 안에 각 아이템으로 표시할 뷰를 반환하는 getView() 메소드 정의|
|리스트뷰  다루기 | 화면에 리스트뷰를 추가하고 아이템이 선택되었을 때 호출될 리스너 객체 정의|

BaseAdpater 클래스는 어댑터 클래스를 정의할 때 상속하는 가장 일반적인 클래스로 추상 클래스이다. 이 클래스에는 어댑터 클래스가 기본적으로 가져야 하는 메소드들이 정의되어 있으며, 그 중에 getCounte()와 getView() 메소드를 주의 깊게 보아야 한다. getCount() 메소드는 이 어댑터에서 관리하는 아이템의 개수를 리턴해야 한다. 그런데 리스트 뷰는 각각의 아이템을 인덱스로 구분하는데 인덱스를 사용하기 위해서는 전체 아이템의 개수를 알아야하므로 이 개수가 잘못 반환되면 심각한 오류가 발생할 수 있다. getView() 메소드에서 리턴할 뷰를 잘 구성하는 것이 중요하다. 결국 BaseAdapter 클래스를 상속해 정의하는 어댑터 클래스들은 이 두가지 메소드를 먼저 파악하거나 구성한 후 다른 메소드들을 보완하여 완성해야 한다.

	public View getView(int position, View convertView, ViewGroup parent)
    
첫 파라미터는 아이템의 인덱스를 의미하는 것으로 리스트뷰에서 보일 아이템의 위치 정보라고 할 수 있다.<br>
0부터 시작하여 아이템의 개수만큼 파라미터로 전달된다.

두 번째 파라미터로 전달되는 뷰는 현재 인덱스에 해당하는 뷰 객체를 의미한다. 선택 위젯은 데이터가 많아 스크롤될 때 재활용하는 메커니즘을 가지고 있어 한 번 만들어진 뷰가 화면에 그대로 다시 보일 수 있도록 되어 있다. 따라서 이 뷰가 널값이 아니면 재활용할 수 있다. 예로 리스트뷰가 가진 아이템은 10개 인데 화면에서 한 번에 보여 줄 수 있는 아이템의 개수가 5개라면 5개가 먼저 보여 진 후 스크롤될 때마다 이미 만들어진 뷰들을 그대로 사용하면서 데이터만 바꾸어 보여주는 방식을 사용한다.

세 번째 파라미터는 이 뷰를 포함하고 있는 부모 컨테이너 객체이다.

	@Override
    	public View getView(int pos, View convertView, ViewGroup viewGroup){
            SingerItemView view = new SingerItemView(getApplicationContext());
            SingerItem item = items.get(pos);
            view.setName(item.getName());
            view.setMobile(item.getMobile());
            view.setImage(item.getResId());
            return view;
        }
convertView 객체가 널 값인지를 체크하고 있다. 만약 널 값이라면 새로 SingerItemView객체를 만들고 널 값이 아니라면 convertView에 데이터를 설정하고 반환하는 것을 알 수 있다. 데이터가 들어있는 객체는 리스트 안에 있는 SingerItem 객체이고 이 객체는 position 파라미터와 마찬가지로 인덱스로 구분될 수 있으므로 position 파라미터는 리스트 안에 있는 SingerItem 객체를 가져오는 데 그대로 사용한다. 다른 메소드들은 리스트에 데이터를 넣거나 빼기 위한 것으로 필요한 메소드들을 더 추가할 수 있다.

## 스피너
여러 아이템 중에서 하나를 선택하는 전형적인 위젯으로 일반적으로 윈도우에서 콤보박스로 불리는 그것과 같은 기능을 한다.

스피너 객체도 선택 위젯이므로 setAdapter() 메소드의 파라미터로 어댑터 객체를 전달해야 한다. 그런데 리스트뷰를 만들 때는 직접 정의했던 어댑터가 여기에서는 정의하지 않고 두 줄의 코드만 추가한 것을 볼 수 있다. 이 코드는 문자열로만 구성된 아이템들을 스피너로 보여줄 때 사용하는 것으로 배열로 된 데이터를 이용하는 ArrayAdapter 객체를 하나 만든 후 그 객체의  setDropDownViewResource() 메소드로 아이템들을 보여줄 ㄷ뷰의 레이아웃을 지정하면 된다. ArrayAdapter를 만들 때 사용한 생성자를 보면 다음과 같다.
	
    public ArrayAdapter(Context context, intTextViewResourceId, T[] objects)
    
첫 번째 파라미터는 Context 객체이므로 액티비티인 this를 전달하면 된다. 두 번째 파라미터는 뷰를 초기화할 때 사용되는 XML레이아웃의 리소스 ID값으로 이 코드에서는 android.R.layout.simple_spinner_item을 전달하였다. 이 ID 값으로 안드로이드 API에 들어 있는 미리 정의된 레이아웃으로 문자열을 아이템으로 보여주는 단순 스피너 아이템의 레이아웃이라고 보면 된다. 이 레이아웃 안에는 텍스트 뷰 객체가 들어 있으며, 이렇게 정해 놓은 리소스 ID 값은 안드로이드 개발자가 미리 정의한 것이므로 이 값을 그대로 사용해야 한다. 세 번째 파라미터는 아이템으로 보일 문자열 데이터들의 배열이다.
    
    ArrayAdapter<String> adapter 
    			= new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, items);
    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
    spinner.setAdapter(adapter);
    

가장 단순한 형태의 리스트뷰

	public class MainActivity extends ListActivity {
		...
        String[] items = {...};

		public void onCreate(...){
        	...
            setListAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, items));
            ...
        }
        
    	protected void onListItemClick(ListView l, View v, int position, long id){
        	super.onListItem(l, v, position, id);
            String text = " position" + position + " " + items[position];    
        }
   	}
    
문자열 하나만 보여주는 리스트뷰를 화면에 보여주는 방법은 아주 간단하게 만들 수 있다. 이는 안드로이드에서 리스트뷰를 쉽게 구성할 수 있도록 ListActivity라는 별도의 액티비티를 제공하기 때문이다.

## 그리드 뷰

테이블(Table) 형태와 유사하게 데이터를 보여준다.

어댑터를 이용해 그리드뷰에 들어가는 데이터를 설정하고 getView() 메소드를 이용해 각 아이템이 표현하는 모양을 결정한다.
그리드 뷰는 2차원적인 데이터, 즉 행과 열이 있는 데이터이다. 그럼에도 선택 위젯의 속성을 그대로 따라가고 있어 일차원적인 데이터로 취급한다.
        
	 
----
디자인 패턴
--

빌더 패턴 
1. http://jdm.kr/blog/217
2. https://ko.wikipedia.org/wiki/%EB%B9%8C%EB%8D%94_%ED%8C%A8%ED%84%B4
