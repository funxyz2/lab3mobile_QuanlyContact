package com.example.quanlycontact;

import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private DatabaseHandler db;
    private List<Contact> contactList;
    private ContactAdapter adapter;
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        db = new DatabaseHandler(this);

        // Data mẫu
        db.addContact(new Contact("Ravi",     "9100000000"));
        db.addContact(new Contact("Srinivas", "9199999999"));
        db.addContact(new Contact("Tommy",    "9522222222"));
        db.addContact(new Contact("Karthik",  "9533333333"));
        db.addContact(new Contact("Ravi",     "9100000000"));
        db.addContact(new Contact("Srinivas", "9199999999"));
        db.addContact(new Contact("Tommy",    "9522222222"));
        db.addContact(new Contact("Karthik",  "9533333333"));

        contactList = db.getAllContacts();
        adapter = new ContactAdapter(this, contactList);

        listView = findViewById(R.id.lv_contact);
        listView.setAdapter(adapter);

        Log.e("Reading: ", "Reading all contacts..");
        List<Contact> contacts = db.getAllContacts();
        for (Contact cn : contacts) {
            String log = "Id: " + cn.getID() + ", Name: " + cn.getName()
                    + ", Phone: " + cn.getPhoneNumber();
            Log.e("Name: ", log);
        }

        // Long click → xóa contact
        listView.setOnItemLongClickListener((parent, view, position, id) -> {
            Contact selected = contactList.get(position);
            db.deleteContact(selected);
            contactList.remove(position);
            adapter.notifyDataSetChanged();
            Toast.makeText(this, "Đã xóa: " + selected.getName(), Toast.LENGTH_SHORT).show();
            return true;
        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}