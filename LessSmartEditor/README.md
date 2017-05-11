# LessSmartEditor ( 2017. 05. 11~ )

간단한 Android Editor App 개발. 개발 요구사항은 다음과 같다.

1.	에디터에서 작성할 수 있는 컴포넌트의 종류는 글, 그림, 지도가 있다.
2.	에디터에 위 컴포넌트 중 하나를 추가할 때에는 <추가>버튼을 누른 후, 나오는 목록 중에 하나를 선택하는 방식으로 한다.
3.	컴포넌트를 추가하고 제거하는 것은 RecyclerView를 활용하도록 한다.
4.	최초의 작성된 컴포넌트들은 삭제는 가능하나 서로의 위치를 옮기는 등의 기능은 없도록 한다.(단, 글의 경우엔 수정 가능)
5.	지도 컴포넌트는 Retrofit을 사용하여 Naver 지도 Open API와 SDK를 활용하도록 한다.
6.	지도와 그림 컴포넌트는 고정된 크기로만 삽입된다.
7.	그림 삽입 시 Glide를 활용한다.
8.	저장 버튼을 누를 시 각 컴포넌트의 위치와 정보가 내장된 데이터베이스에 저장되도록 한다. 이 때 SQLite를 활용하여 구현한다.
9.	불러오기 기능을 통해서 데이터베이스에 저장되어 있는 글들을 불러 올 수 있다.
10.	아래 그림의 디자인은 예시이며, 따라할 필요 없이 디자인은 각자 구현한다. 단, 동일한 기능의 동작이 가능해야 한다.

<img src="example.png">

-------------

### INFO

RecyclerView & ViewHolder 동작과정

JSON & SQLITE<br>
http://stackoverflow.com/questions/16603621/how-to-store-json-object-in-sqlite-database

Partial Bold<br>
http://stackoverflow.com/questions/14371092/how-to-make-a-specific-text-on-textview-bold
http://stackoverflow.com/questions/1529068/is-it-possible-to-have-multiple-styles-inside-a-textview

Text Watcher<br>
http://stackoverflow.com/questions/7818438/how-to-insert-image-to-a-edittext

Check truncated<br>
http://stackoverflow.com/questions/2923345/is-there-a-way-to-check-if-a-textviews-text-is-truncated


how to create different view type in recyclerView
http://stackoverflow.com/questions/26245139/how-to-create-recyclerview-with-multiple-view-type?rq=1

http://stackoverflow.com/questions/25914003/recyclerview-and-handling-different-type-of-row-inflation/29362643#29362643

http://stackoverflow.com/questions/39971350/recycle-view-inflating-different-row-getting-exception-while-binding-the-data/39972276#39972276
—————

### ISSUE LOG

5/11(목)
android.view.WindowLeaked in Custom Dialog<br>
http://sjava.net/2011/11/androidviewwindowleaked-%EB%AC%B8%EC%A0%9C%EC%97%90-%EB%8C%80%ED%95%9C-%ED%95%B4%EA%B2%B0%EC%B1%85/

RecyclerView 순서 강제<br>
http://stackoverflow.com/questions/32065267/recyclerview-changing-items-during-scroll
