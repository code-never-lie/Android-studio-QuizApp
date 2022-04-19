package com.example.syedsubtainraza.quizapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.example.syedsubtainraza.quizapp.QuizContract.*;

import java.util.ArrayList;
import java.util.List;


public class QuizDbHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "MyQuizApp.db";
    private static final int DATABASE_VERSION = 1;

    private static QuizDbHelper instance;

    private SQLiteDatabase db;

    private QuizDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public static synchronized QuizDbHelper getInstance(Context context) {
        if (instance == null) {
            instance = new QuizDbHelper(context.getApplicationContext());
        }
        return instance;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        this.db = db;

        final String SQL_CREATE_CATEGORIES_TABLE = "CREATE TABLE " +
                CategoriesTable.TABLE_NAME + "( " +
                CategoriesTable._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                CategoriesTable.COLUMN_NAME + " TEXT " +
                ")";

        final String SQL_CREATE_QUESTIONS_TABLE = "CREATE TABLE " +
                QuestionsTable.TABLE_NAME + " ( " +
                QuestionsTable._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                QuestionsTable.COLUMN_QUESTION + " TEXT, " +
                QuestionsTable.COLUMN_OPTION1 + " TEXT, " +
                QuestionsTable.COLUMN_OPTION2 + " TEXT, " +
                QuestionsTable.COLUMN_OPTION3 + " TEXT, " +
                QuestionsTable.COLUMN_ANSWER_Nr + " INTEGER, " +
                QuestionsTable.COLUMN_DIFFICULTY + " TEXT, " +
                QuestionsTable.COLUMN_CATEGORY_ID + " INTEGER, " +
                "FOREIGN KEY(" + QuestionsTable.COLUMN_CATEGORY_ID + ") REFERENCES " +
                CategoriesTable.TABLE_NAME + "(" + CategoriesTable._ID + ")" + "ON DELETE CASCADE" +
                ")";

        db.execSQL(SQL_CREATE_CATEGORIES_TABLE);
        db.execSQL(SQL_CREATE_QUESTIONS_TABLE);
        fillCategoriesTable();
        fillQuestionsTable();
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + CategoriesTable.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + QuestionsTable.TABLE_NAME);
        onCreate(db);
    }

    @Override
    public void onConfigure(SQLiteDatabase db) {
        super.onConfigure(db);
        db.setForeignKeyConstraintsEnabled(true);
    }

    private void fillCategoriesTable() {
        Category c1 = new Category("Programming");
        addCategory(c1);
        Category c2 = new Category("Database");
        addCategory(c2);
        Category c3 = new Category("Android App");
        addCategory(c3);
        Category c4 = new Category("Maths");
        addCategory(c4);
    }

    private void addCategory(Category category) {
        ContentValues cv = new ContentValues();
        cv.put(CategoriesTable.COLUMN_NAME, category.getName());
        db.insert(CategoriesTable.TABLE_NAME, null, cv);
    }

    private void fillQuestionsTable() {

        Question q1 = new Question("Programming, Easy: Which data type is used to represent the absence of parameters?",
                "int", "void", "short", 2,
                Question.DIFFICULTY_EASY, Category.PROGRAMMING);
        addQuestion(q1);

        Question q2 = new Question("Programming, Easy: Which data type is used to represent the absence of parameters?",
                "64", "128", "256", 2,
                Question.DIFFICULTY_EASY, Category.PROGRAMMING);
        addQuestion(q2);

        Question q3 = new Question("Programming, Easy:  Which of the following is not one of the sizes of the floating point types?",
                "short float", "float", "long float", 1,
                Question.DIFFICULTY_EASY, Category.PROGRAMMING);
        addQuestion(q3);

        Question q4 = new Question("Programming, Easy: The size of an object or a type can be determined using which operator?",
                "malloc", "calloc", "sizeof", 3,
                Question.DIFFICULTY_EASY, Category.PROGRAMMING);
        addQuestion(q4);

        Question q5 = new Question("Programming, Easy: Which of the following will not return a value?",
                "void", "null", "empty", 1,
                Question.DIFFICULTY_EASY, Category.PROGRAMMING);
        addQuestion(q5);

        Question q6 = new Question("Programming, Medium:  How many types of returning values are present in c++?",
                "1", "2", "3", 3,
                Question.DIFFICULTY_MEDIUM, Category.PROGRAMMING);
        addQuestion(q6);

        Question q7 = new Question("Programming, Medium:   What will you use if you are not intended to get a return value??",
                "static", "void", "volatile", 2,
                Question.DIFFICULTY_MEDIUM, Category.PROGRAMMING);
        addQuestion(q7);

        Question q8 = new Question("Programming, Medium:  The operator used for dereferencing or indirection is ____",
                "*", "&", "->", 1,
                Question.DIFFICULTY_MEDIUM, Category.PROGRAMMING);
        addQuestion(q8);

        Question q9 = new Question("Programming, Medium: Which of the following is illegal?",
                "int i; double* dp = &i ", "string s, *sp = 0;", "int *ip;", 3,
                Question.DIFFICULTY_MEDIUM, Category.PROGRAMMING);
        addQuestion(q9);

        Question q10 = new Question("Programming, Medium: Can two functions declare variables(non static) with the same name.",
                "No", "Yes", "Yes, but not a very efficient way to write programs", 3,
                Question.DIFFICULTY_MEDIUM, Category.PROGRAMMING);
        addQuestion(q10);

        Question q11 = new Question("Programming, Hard:  Which of the following correctly declares an array?",
                "int array[10];", "int array;", "array{10};", 1,
                Question.DIFFICULTY_HARD, Category.PROGRAMMING);
        addQuestion(q11);

        Question q12 = new Question("Programming, Hard:  What is the index number of the last element of an array with 9 elements?",
                "9", "8", "0", 2,
                Question.DIFFICULTY_HARD, Category.PROGRAMMING);
        addQuestion(q12);

        Question q13 = new Question("Programming, Hard:  Which of the following accesses the seventh element stored in array?",
                "array[6]", "array[6]", "array(6)", 1,
                Question.DIFFICULTY_HARD, Category.PROGRAMMING);
        addQuestion(q13);

        Question q14 = new Question("Programming, Hard:  Which of the following gives the memory address of the first element in array?",
                "array[0]", "array[1]", "array", 3,
                Question.DIFFICULTY_HARD, Category.PROGRAMMING);
        addQuestion(q14);

        Question q15 = new Question("Programming, Hard:  The data elements in the structure are also known as what?",
                "objects", "members", "data", 2,
                Question.DIFFICULTY_HARD, Category.PROGRAMMING);
        addQuestion(q15);

        Question q16 = new Question("Database, Easy: Which one of the following is used to define the structure of the relation, deleting relations and relating schemas? ",
                "DML(Data Manipulation Langauge)", "DDL(Data Definition Langauge)", "Relational Schema", 2,
                Question.DIFFICULTY_EASY, Category.DATABASE);
        addQuestion(q16);

        Question q17 = new Question("Database, Easy: The basic data type char(n) is a _____ length character string and varchar(n) is _____ length character.",
                "Fixed, equal", "Equal, variable", "Fixed, variable", 3,
                Question.DIFFICULTY_EASY, Category.DATABASE);
        addQuestion(q17);

        Question q18 = new Question("Database, Easy: To remove a relation from an SQL database, we use the ______ command.",
                "Delete", "Drop Table", "Remove", 2,
                Question.DIFFICULTY_EASY, Category.DATABASE);
        addQuestion(q18);

        Question q19 = new Question("Database, Easy: Which of these query will display the the table given above ?",
                " Select name from employee", "Select name", "Select employee from name", 1,
                Question.DIFFICULTY_EASY, Category.DATABASE);
        addQuestion(q19);

        Question q20 = new Question("Database, Easy: The ________ clause is used to list the attributes desired in the result of a query.",
                "Where", "Select", "From", 2,
                Question.DIFFICULTY_EASY, Category.DATABASE);
        addQuestion(q20);

        Question q21 = new Question("Database, Medium: By default, the order by clause lists items in ______ order.  ",
                "Descending", "Ascending", "Any", 2,
                Question.DIFFICULTY_MEDIUM, Category.DATABASE);
        addQuestion(q21);

        Question q22 = new Question("Database, Medium: The intersection operator is used to get the _____ tuples. ",
                "Different", "Common", "Repeating", 2,
                Question.DIFFICULTY_MEDIUM, Category.DATABASE);
        addQuestion(q22);

        Question q23 = new Question("Database, Medium: The union operation automatically __________ unlike the select clause. ",
                "Eliminates duplicate", "Adds tuples", "Adds common tuples", 1,
                Question.DIFFICULTY_MEDIUM, Category.DATABASE);
        addQuestion(q23);

        Question q24 = new Question("Database, Medium: If we want to retain all duplicates, we must write ________ in place of union. ",
                "Intersect some", "Union some", "Union all", 3,
                Question.DIFFICULTY_MEDIUM, Category.DATABASE);
        addQuestion(q24);

        Question q25 = new Question("Database, Medium: A _____ indicates an absent value that may exist but be unknown or that may not exist at all. ",
                "Empty Tuple", "New Value","Null Value", 3,
                Question.DIFFICULTY_MEDIUM, Category.DATABASE);
        addQuestion(q25);

        Question q26 = new Question("Database, Hard: In an employee table to include the attributes whose value always have some value which of the following constraint must be used? ",
                "Null", "Not Null", "Unique", 2,
                Question.DIFFICULTY_HARD, Category.DATABASE);
        addQuestion(q26);

        Question q27 =  new Question("Database, Hard:  Using the ______ clause retains only one copy of such identical tuples. ",
                "Distinct", "Null", "Unique", 1,
                Question.DIFFICULTY_HARD, Category.DATABASE);
        addQuestion(q27);

        Question q28 = new Question("Database, Hard: The primary key must be ",
                "Unique", "Not null", "Both Unique or not null", 3,
                Question.DIFFICULTY_HARD, Category.DATABASE);
        addQuestion(q28);

        Question q29 = new Question("Database, Hard: The result of _____unknown is unknown. ",
                "AND", "NOT", "OR", 2,
                Question.DIFFICULTY_HARD, Category.DATABASE);
        addQuestion(q29);

        Question q30 = new Question("Database, Hard: Which of the following creates a virtual relation for storing the query? ",
                "View", "Function", "Procedure", 1,
                Question.DIFFICULTY_HARD, Category.DATABASE);
        addQuestion(q30);

        Question q31 = new Question("Android App, Easy: A type of service provided by android that shows messages and alerts to user is",
                "Content Provider", "View System", "Notification Manager", 3,
                Question.DIFFICULTY_EASY, Category.ANDROID_APP);
        addQuestion(q31);

        Question q32 = new Question("Android App, Easy: Broadcast that includes information about battery state, level, etc. is",
                "android.intent.action.BATTERY_CHANGED", "android.intent.action.BATTERY_LOW", "android.intent.action.BATTERY_OKAY", 1,
                Question.DIFFICULTY_EASY, Category.ANDROID_APP);
        addQuestion(q32);
        Question q33 = new Question("Android App, Easy: One of application component, that manages application's background services is called",
                "Activities", "Services", "Broadcast Receivers", 2,
                Question.DIFFICULTY_EASY, Category.ANDROID_APP);
        addQuestion(q33);

        Question q34 = new Question("Android App, Easy: In android studio, callback that is called when activity interaction with user is started is",
                "onStart", "onStop", "onResume", 3,
                Question.DIFFICULTY_EASY, Category.ANDROID_APP);
        addQuestion(q34);

        Question q35 = new Question("Android App, Easy: Tab that can be used to do any task that can be done from DOS window is",
                "terminal", "messages", "TODO", 1,
                Question.DIFFICULTY_EASY, Category.ANDROID_APP);
        addQuestion(q35);

        Question q36 = new Question("Android App, Medium:  Space outside of widget can be customized using",
                "margins", "padding", "height", 1,
                Question.DIFFICULTY_MEDIUM, Category.ANDROID_APP);
        addQuestion(q36);

        Question q37 = new Question("Android App, Medium: Tab that shows hierarchy of project is",
                "Build variants", "Structure", "Favorites", 2,
                Question.DIFFICULTY_MEDIUM, Category.ANDROID_APP);
        addQuestion(q37);

        Question q38 = new Question("Android App, Medium: In android, compiled code is executed by part of android system called",
                "API", "DEX", "DVM", 3,
                Question.DIFFICULTY_MEDIUM, Category.ANDROID_APP);
        addQuestion(q38);

        Question q39 = new Question("Android App, Medium: A part of android studio, that work as a simulator for android devices is called",
                "driver", "emulator", "stub", 2,
                Question.DIFFICULTY_MEDIUM, Category.ANDROID_APP);
        addQuestion(q39);

        Question q40 = new Question("Android App, Medium: Code that provide easy way to use all android features is called",
                "API", "DEX", "DVM", 1,
                Question.DIFFICULTY_MEDIUM, Category.ANDROID_APP);
        addQuestion(q40);

        Question q41 = new Question("Android App, Hard:  If a button is clicked whose method in Java is not defined then android application will",
                "do nothing", "crash", "show black screen", 2,
                Question.DIFFICULTY_HARD, Category.ANDROID_APP);
        addQuestion(q41);

        Question q42 = new Question("Android App, Hard: Android content provider architecture, component that does not resides on data layer is ",
                "content provider", "SQLite", "Internet", 1,
                Question.DIFFICULTY_HARD, Category.ANDROID_APP);
        addQuestion(q42);

        Question q43 = new Question("Android App, Hard:  Android calculates pixels density of mobile (currently using app) using unit of measurement",
                "CP", "SP", "DP", 3,
                Question.DIFFICULTY_HARD, Category.ANDROID_APP);
        addQuestion(q43);

        Question q44 = new Question("Android App, Hard:  In android studio, each new activity created must be defined in",
                "Build.gradle", "AndroidManifest.xml", "res/values", 2,
                Question.DIFFICULTY_HARD, Category.ANDROID_APP);
        addQuestion(q44);

        Question q45 = new Question("Android App, Hard:  Android component that controls external elements of file is called",
                "intent", "manifest", "resources", 3,
                Question.DIFFICULTY_HARD, Category.ANDROID_APP);
        addQuestion(q45);

        Question q46 = new Question("Maths, Easy: 0.003 x 0.02 = ?",
                "0.00006", "0.0006", "0.006", 1,
                Question.DIFFICULTY_EASY, Category.MATHS);
        addQuestion(q46);

        Question q47 = new Question("Maths, Easy: What is the average of the numbers: 0, 0, 4, 10, 5, and 5?",
                "2", "3", "4", 3,
                Question.DIFFICULTY_EASY, Category.MATHS);
        addQuestion(q47);

        Question q48 = new Question("Maths, Easy: What is the rate of discount if a bicycle which cost Rs.4,000 is sold for Rs.3,200?",
                "14%", "20%", "18%", 2,
                Question.DIFFICULTY_EASY, Category.MATHS);
        addQuestion(q48);

        Question q49 = new Question("Maths, Easy: |-4| + |4| - 4 + 4 = ?",
                "0", "4", "8", 3,
                Question.DIFFICULTY_EASY, Category.MATHS);
        addQuestion(q49);

        Question q50 = new Question("Maths, Easy: What is the value of x in 3x - 15 - 6 = 0 ?",
                "7", "8", "9", 1,
                Question.DIFFICULTY_EASY, Category.MATHS);
        addQuestion(q50);

        Question q51 = new Question("Maths, Medium: In a class of 40 students 20% are girls. How many boys are there in the class? ",
                "32", "30", "28", 1,
                Question.DIFFICULTY_MEDIUM, Category.MATHS);
        addQuestion(q51);

        Question q52 = new Question("Maths, Medium: What is the distance travelled by a car which travelled at a speed of 80km/hr for 3 hours and 30 minutes?",
                "275KM", "280KM", "285KM", 2,
                Question.DIFFICULTY_MEDIUM, Category.MATHS);
        addQuestion(q52);

        Question q53 = new Question("Maths, Medium: What comes next in the sequence: 1, 3, 11, 43, ____?",
                "161", "181", "171", 3,
                Question.DIFFICULTY_MEDIUM, Category.MATHS);
        addQuestion(q53);

        Question q54 = new Question("Maths, Medium: If A completes a particular work in 8 days and B the same work in 24 days. How many days will it take if they work together?",
                "4", "5", "6", 3,
                Question.DIFFICULTY_MEDIUM, Category.MATHS);
        addQuestion(q54);

        Question q55 = new Question("Maths, Medium: Sachin is younger than Rahul by 7 years. If their ages are in the respective ratio of 7 : 9, how old is Sachin?",
                "24.5 years ", "28 years", "28 years", 1,
                Question.DIFFICULTY_MEDIUM, Category.MATHS);
        addQuestion(q55);

        Question q56 = new Question("Maths, Hard: The period of 2 sin x cos x is ",
                " π ", "4 π 2", "2 π", 1,
                Question.DIFFICULTY_HARD, Category.MATHS);
        addQuestion(q56);

        Question q57 = new Question("Maths, Hard: The period of | sin (3x) | is ",
                "3 π", "2 π / 3 ", "π / 3 ", 3,
                Question.DIFFICULTY_HARD, Category.MATHS);
        addQuestion(q57);

        Question q58 = new Question("Maths, Hard: If f(x) is an odd function, then | f(x) | is",
                "an odd function ", "an even function ", "neither odd nor even ", 2,
                Question.DIFFICULTY_HARD, Category.MATHS);
        addQuestion(q58);

        Question q59 = new Question("Maths, Hard: The graphs of the two linear equations a x + b y = c and b x - a y = c, where a, b and c are all not equal to zero, ",
                "perpendicular", "are parallel", "intersect at one point ", 1,
                Question.DIFFICULTY_HARD, Category.MATHS);
        addQuestion(q59);

        Question q60 = new Question("Maths, Hard: If Logx (1 / 8) = - 3 / 2, then x is equal to ",
                "-4", "4", "1/4", 2,
                Question.DIFFICULTY_HARD, Category.MATHS);
        addQuestion(q60);


    }

    private void addQuestion(Question question) {
        ContentValues cv = new ContentValues();
        cv.put(QuestionsTable.COLUMN_QUESTION, question.getQuestion());
        cv.put(QuestionsTable.COLUMN_OPTION1, question.getOption1());
        cv.put(QuestionsTable.COLUMN_OPTION2, question.getOption2());
        cv.put(QuestionsTable.COLUMN_OPTION3, question.getOption3());
        cv.put(QuestionsTable.COLUMN_ANSWER_Nr, question.getAnswer_Nr());
        cv.put(QuestionsTable.COLUMN_DIFFICULTY, question.getDifficulty());
        cv.put(QuestionsTable.COLUMN_CATEGORY_ID, question.getCategoryID());
        db.insert(QuestionsTable.TABLE_NAME, null, cv);
    }

    public List<Category> getAllCategories() {
        List<Category> categoryList = new ArrayList<>();
        db = getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM " + CategoriesTable.TABLE_NAME, null);

        if (c.moveToFirst()) {
            do {
                Category category = new Category();
                category.setId(c.getInt(c.getColumnIndex(CategoriesTable._ID)));
                category.setName(c.getString(c.getColumnIndex(CategoriesTable.COLUMN_NAME)));
                categoryList.add(category);
            } while (c.moveToNext());
        }

        c.close();
        return categoryList;
    }

    public ArrayList<Question> getAllQuestions() {
        ArrayList<Question> questionList = new ArrayList<>();
        db = getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM " + QuestionsTable.TABLE_NAME, null);

        if (c.moveToFirst()) {
            do {
                Question question = new Question();
                question.setId(c.getInt(c.getColumnIndex(QuestionsTable._ID)));
                question.setQuestion(c.getString(c.getColumnIndex(QuestionsTable.COLUMN_QUESTION)));
                question.setOption1(c.getString(c.getColumnIndex(QuestionsTable.COLUMN_OPTION1)));
                question.setOption2(c.getString(c.getColumnIndex(QuestionsTable.COLUMN_OPTION2)));
                question.setOption3(c.getString(c.getColumnIndex(QuestionsTable.COLUMN_OPTION3)));
                question.setAnswer_Nr(c.getInt(c.getColumnIndex(QuestionsTable.COLUMN_ANSWER_Nr)));
                question.setDifficulty(c.getString(c.getColumnIndex(QuestionsTable.COLUMN_DIFFICULTY)));
                question.setCategoryID(c.getInt(c.getColumnIndex(QuestionsTable.COLUMN_CATEGORY_ID)));
                questionList.add(question);
            } while (c.moveToNext());
        }

        c.close();
        return questionList;
    }

    public ArrayList<Question> getQuestions(int categoryID, String difficulty) {
        ArrayList<Question> questionList = new ArrayList<>();
        db = getReadableDatabase();

        String selection = QuestionsTable.COLUMN_CATEGORY_ID + " = ? " +
                " AND " + QuestionsTable.COLUMN_DIFFICULTY + " = ? ";
        String[] selectionArgs = new String[]{String.valueOf(categoryID), difficulty};

        Cursor c = db.query(
                QuestionsTable.TABLE_NAME,
                null,
                selection,
                selectionArgs,
                null,
                null,
                null
        );

        if (c.moveToFirst()) {
            do {
                Question question = new Question();
                question.setId(c.getInt(c.getColumnIndex(QuestionsTable._ID)));
                question.setQuestion(c.getString(c.getColumnIndex(QuestionsTable.COLUMN_QUESTION)));
                question.setOption1(c.getString(c.getColumnIndex(QuestionsTable.COLUMN_OPTION1)));
                question.setOption2(c.getString(c.getColumnIndex(QuestionsTable.COLUMN_OPTION2)));
                question.setOption3(c.getString(c.getColumnIndex(QuestionsTable.COLUMN_OPTION3)));
                question.setAnswer_Nr(c.getInt(c.getColumnIndex(QuestionsTable.COLUMN_ANSWER_Nr)));
                question.setDifficulty(c.getString(c.getColumnIndex(QuestionsTable.COLUMN_DIFFICULTY)));
                question.setCategoryID(c.getInt(c.getColumnIndex(QuestionsTable.COLUMN_CATEGORY_ID)));
                questionList.add(question);
            } while (c.moveToNext());
        }

        c.close();
        return questionList;
    }
}
