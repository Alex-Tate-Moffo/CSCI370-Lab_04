package com.example.asynctask;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = findViewById(R.id.student);

        StudentAsyncTask task = new StudentAsyncTask(listView, this);
        task.execute();
    }

    public class Student {
        private String lastName;
        private String firstName;
        private String major;

        public Student(String lastName, String firstName, String major) {
            this.lastName = lastName;
            this.firstName = firstName;
            this.major = major;
        }

        public String getLastName() {
            return lastName;
        }

        public void setLastName(String lastName) {
            this.lastName = lastName;
        }

        public String getFirstName() {
            return firstName;
        }

        public void setFirstName(String firstName) {
            this.firstName = firstName;
        }

        public String getMajor() {
            return major;
        }

        public void setMajor(String major) {
            this.major = major;
        }
    }

    public class StudentAdapter extends BaseAdapter {
        private Context mContext;
        private LayoutInflater mInflator;
        private ArrayList<Student> mDataSource;

        public StudentAdapter(Context context, ArrayList<Student> students) {
            mContext = context;
            mDataSource = students;
            mInflator = (LayoutInflater) mContext.getSystemService((Context.LAYOUT_INFLATER_SERVICE));

        }


        @Override
        public int getCount() {
            return mDataSource.size();
        }

        @Override
        public Object getItem(int position) {
            return mDataSource.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View rowView = mInflator.inflate(R.layout.list_view_row, parent, false);

            TextView firstName = rowView.findViewById(R.id.first_name);
            TextView lastName = rowView.findViewById(R.id.last_name);
            TextView major = rowView.findViewById(R.id.major);

            Student student = (Student) getItem(position);
            firstName.setText(student.getFirstName());
            lastName.setText(student.getLastName());
            major.setText(student.getMajor());

            return rowView;
        }
    }

    public class StudentDao {
        private ArrayList<Student> studentList;

        public StudentDao() {
            studentList = new ArrayList<Student>();

            studentList.add(new Student("Bob", "Billy", "Biology"));
            studentList.add(new Student("Smith", "John", "English"));
            studentList.add(new Student("Doe", "Jane", "Sociology"));
            studentList.add(new Student("Robinson", "Max", "Computer Science"));
            studentList.add(new Student("John", "Elton", "Theater"));
        }

        public ArrayList<Student> getAllStudents(){
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            return studentList;
        }

    }

    public class StudentAsyncTask extends AsyncTask <Void,Void,ArrayList<Student>> {

        private Context mContext;
        private ListView listView;

        private ArrayList students;

        public StudentAsyncTask(ListView view, Context con) {
            this.listView = view;
            this.mContext = con;
        }

        @Override
        protected ArrayList<Student> doInBackground(Void... voids) {
            StudentDao list = new StudentDao();
            students = list.getAllStudents();
            return list.getAllStudents();
        }

        @Override
        protected void onPostExecute(ArrayList<Student> students) {
            super.onPostExecute(students);

            StudentAdapter adapter = new StudentAdapter(this.mContext, students);

            this.listView.setAdapter(adapter);
        }
    }

}