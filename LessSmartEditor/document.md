# LessSmartEditor
-------

### 목차

1. 요구 사항<br><br>
1. 구현 설계<br>
	a. 데이터 구조 및 처리 방식<br>
	b. 컴포넌트<br>
	c. ViewHolder<br><br>
1. 계획<br><br>


------------
### 1. 요구 사항




-----------


### 2. 구현 설계

#### a. 데이터 구조 및 처리

***데이터 구조***
~~~
COMPONENT TYPE VALUE :
	TEXT_COMPONENT = 0;
    IMG_COMPONENT = 1;
    MAP_COMPONENT = 2;

Document {

	Id : 문서 식별자
	Title : Document 제목
    TimeStemp : Document 작성 시간

	TextComponent {
		String text : 글 내용에 대한 Data
		int componentType : 컴포넌트 타입 속성값
		int index : 해당 컴포넌트가 갖는 document 내에서의 위치 정보
	}

	ImgCompenent {
		String imgSrc(URI, URL) : 이미지 경로 정보
		int componentType : 컴포넌트 타입 속성값
		int index : 해당 컴포넌트가 갖는 document 내에서의 위치 정보
	}

	MapComponent {
		String placeInform : 위치에 대한 주소 정보 (건물명, 주소..)
		double CoordsX, CoordsY : 해당 위치에 대한 좌표 정보 (위도, 경도)
		URI staticMapImgSrc : 이미지 경로 정보
		int componentType : 컴포넌트 타입 속성값
		int index : 해당 컴포넌트가 갖는 document 내에서의 위치 정보
	}
}
~~~

***데이터 처리***

문서 내부 컴포넌트들은 직렬화 과정을 통해 JSON으로 파싱되며, 이 때 컴포넌트들은는 위에서 아래로 순서대로 처리된다.

=> 컴포넌트의 문서 내 위치가 자동으로 결정된다.



<img src="process.png">

-------


#### b. 컴포넌트

##### 텍스트 컴포넌트
-EditText를 추가하고 데이터 저장시 일괄적으로 처리한다.

##### 이미지 컴포넌트
-URL을 사용자로부터 직접 입력 받거나, 디바이스 갤러리에 접근하여 URI를 받아 Glide로 이미지를 띄운다.

##### 지도 컴포넌트
-SDK를 이용해 지도 상에서 직접 좌표를 입력 받거나, 지역(장소) 검색 API를 통해 좌표를 입력받아 static map API를 호출하여 지도 이미지를 얻어낸다.

-글 작성 시에만 static map API를 통해 이미지를 구하여 이를 내부 저장소에 따로 저장한다.

-글 불러오기 시에는 따로 API를 호출하지 않고 내부 저장소로부터 이미지를 불러온다.

...

Naver Android Map SDK<br>
Naver Android static Map API

tutorial : https://developers.naver.com/docs/map/tutorial/

주소 입력 -> API 요청 -> 좌표 반환 -> 좌표를 통한 static map(IMG) request API -> IMG



-------

#### c.ViewHolder

컴포넌트는 에디터 액티비티 내 RecyclerView에 추가된다.

Adapter는 세 종류의 ViewHolder를 가지며, 컴포넌트 타입에 의해 ViewHolder가 결정된다.

~~~

Adapter {

	Text ViewHolder : 텍스트 컴포넌트의 ViewHolder

	Img ViewHolder : 이미지 컴포넌트의 ViewHolder

  	Map ViewHolder : 지도 컴포넌트의 ViewHolder

}

~~~

--------


### 3. 계획

5/11

데이터 저장 방식 및 구조 설계<br>
앱 동작 설계 및 레이아웃 결정

5/12

텍스트 컴포넌트 추가 동작 구현<br>
Glide를 이용한 이미지 컴포넌트 구현

5/13

텍스트, 이미지 뷰 홀더 및 추가 구현

5/15

네이버 지도 API와 SDK를 이용한 지도 컴포넌트 구현 - 1

5/16

네이버 지도 API와 SDK를 이용한 지도 컴포넌트 구현 - 2


5/17

데이터 처리 - 1 (JSON -> 객체)
지도 뷰 홀더 및 추가 구현

5/18

데이터 처리 - 2 (컴포넌트 -> JSON로 파싱 -> 데이터베이스)
데이터 처리 - 3 (데이터베이스 -> JSON -> 컴포넌트)

5/19

요구 조건 완료 및 점검
테스트 및 개선 - 1

5/20

테스트 및 개선 - 2

5/21

테스트 및 개선 -3
완성

-------



### 추가

1. 텍스트 부분 강조

1. 이미지 & 지도 드래그 드롭으로 위치 변경
