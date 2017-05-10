## 데이터베이스


	public abstract SQLiteDatabase openOrCreateDatabase(String name, int mode, SQLiteDatabase,
    													CursorFactory factory)
    public abstract boolean deleteDatabase(String name)


openOrCreateDatabase(String name, int mode, SQLiteDatabase, CursorFactory factory)
1. 데이터베이스 이름
2. 모드(MODE_PRIVATE, MODE_WORLD_READABLE, MODE_WORLD_WRITEABLE)
- MODE_PRIVATE : 해당 어플리케이션만 데이터베이스에 접근이 가능하다.
- MODE_READALBE : 타 어플리케이션에서 해당 데이터베이스에 읽기 접근이 가능하다.
- MODE_WRITEALBE : 타 어플리에키션에서 해당 데이터베이스에 쓰기 접근이 가능하다.
3. Null이 아닌 객체를 지정할 경우 쿼리의 결과값으로 리턴되는 커서를 만들어낼 객체가 전달된다.


	public void execSQL(String sql) throws SQLExeception

execSQL()을 통해 표준 SQL을 이용하여 할 수 있는 여러 가지 데이터 처리가 가능하다


    db.execSQL("create table if not exists " + name + "("
                + " _id integer PRIMARY KEY autoincrement, "
                + " name text, "
                + " age integer, "
                + " phone text);" );

	db.execSQL( "insert into " + name + "(name, age, phone) values ('John', 20, '010-7788-1234');" );



또한 파라미터를 이용한 삽입, 삭제, 갱신도 가능하다.

	// insert records using parameters

	private int insertRecordParam(String name) {
        println("inserting records using parameters.");

        int count = 1;
        ContentValues recordValues = new ContentValues();

        recordValues.put("name", "Rice");
        recordValues.put("age", 43);
        recordValues.put("phone", "010-3322-9811");
        int rowPosition = (int) db.insert(name, null, recordValues);

        return count;
    }


    // update records using parameters

    private int updateRecordParam(String name) {
        println("updating records using parameters.");

        ContentValues recordValues = new ContentValues();
        recordValues.put("age", 43);
        String[] whereArgs = {"Rice"};

        int rowAffected = db.update(name,
                recordValues,
                "name = ?",
                whereArgs);

        return rowAffected;
    }


    // delete records using parameters

    private int deleteRecordParam(String name) {
        println("deleting records using parameters.");

        String[] whereArgs = {"Rice"};

        int rowAffected = db.delete(name,
                "name = ?",
                whereArgs);

        return rowAffected;
    }

## DB HELPER

DB를 만드는 것 이외에도 테이블의 정의가 바뀌거나 하여 스키마를 업그레이드할 필요가 있을 때 헬퍼 클래스를 사용하는 것도 좋은 방법이다.

	public SQLiteOpenHelper (Context context, String name,
    									SQLiteDatabase.CursorFactory factory, int version)

4번 째 파라미터로 전달되는 정수는 버전 정보를 나타내는 것으로 데이터베이스 업그레이드를 위해 사용하며 기존에 만들어져 있는 데이터베이스의 버전 정보와 다르게 지정하여 데이터베이스의 스키마나 데이터를 바꿀 수 있다.

SQLiteOpenHelper 객체를 만들었다고 해서 데이터베이스 파일이 바로 만들어지는 것은 아니며, 파일이 만들어지도록 하려면 gerReadableDatabase()  또는 getWritable() 메소드를 호출해야 한다.

이 클래스를 이용할 때의 장점은 데이터베이스가 만들어지거나 업그레이드가 필요할 때 콜백 메소드가 호출된다는 점인데 데이터베이스의 생성, 업그레이드 등 여러 가지 상태에 따른 콜백 메소드를 다시 정의하면 각각의 상태에 맞는 처리를 할 수 있다.

	public abstract void onCreate(SQLiteDatabase db)
    public abstract void onOpen(SQLiteDatabase db)
    public abstract void onUpgrade(SQLiteDatbase db)

현재의 데이터베이스 버전이 이미 사용되고 있는 SQLiteDatbase 파일의 버전과 다를 경우에 자동으로 호출되는 onUpgrade() 메소드에는 SQLiteDatabase 객체와 함께 기존 버전 정보를 담고 있는 oldVersion, 현재 버전 정보를 담고 있는 newVersion 파라미터가 전달된다.

----

언제 onCreate(), onOpen(), onUpgrade()가 호출될까? => 강조 부분



**SQLiteDatabase getReadableDatabase ()**

>Create and/or open a database. This will be the same object returned by getWritableDatabase() unless some problem, such as a full disk, requires the database to be opened read-only. In that case, a read-only database object will be returned. If the problem is fixed, a future call to getWritableDatabase() may succeed, in which case the read-only database object will be closed and the read/write object will be returned in the future.
>
>Like getWritableDatabase(), this method may take a long time to return, so you should not call it from the application main thread, including from ContentProvider.onCreate().



**SQLiteDatabase getWritableDatabase ()**
>Create and/or open a database that will be used for reading and writing. **The first time this is called, the database will be opened and onCreate(SQLiteDatabase), onUpgrade(SQLiteDatabase, int, int) and/or onOpen(SQLiteDatabase) will be called.**
>
>Once opened successfully, the database is cached, so you can call this method every time you need to write to the database. (Make sure to call close() when you no longer need the database.) Errors such as bad permissions or a full disk may cause this method to fail, but future attempts may succeed if the problem is fixed.
>
>Database upgrade may take a long time, you should not call this method from the application main thread, including from ContentProvider.onCreate().


---
Reable & Writable Database에 대한 답<br>

If you need to read and write the same database, I suggest you open the database once in writable mode. Writable database can also be read, therefore you should have no problem doing everything you need. According to the getWritableDatabase documentation, this method is used to

> **Create and/or open a database that will be used for reading and writing.**




---

깨알

throws
메소드나 생성자를 수행할 때 발생하는 exception을 선언할 때 사용하는 키워드
throws는 예외를 전가시키는 것
즉, 예외를 자신이 처리하지 않고, 자신을 호출하는 메소드에세 책임을 전가하는 것



throw
실제로 exception을 throw할 때 사용되는 키워드

throw는 강제로 예외를 발생시키는 것
프로그래머의 판단에 따른 처리이다.



throws 메소드를 정의할 때 throws 예약어를 시그니처에 추가하면
그 메소드를 호출하는 곳에서 예외 처리를 해야 한다.

Function throws SomeException 이라는 문장을 이해하면 이해가 쉽다.

Function이 SomeException 예외를 던진다는 뜻

Function을 사용하는 곳(호출하는 곳)을 try 블록으로 감싸준다.




