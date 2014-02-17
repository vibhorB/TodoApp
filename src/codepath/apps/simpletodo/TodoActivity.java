package codepath.apps.simpletodo;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import org.apache.commons.io.FileUtils;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

public class TodoActivity extends Activity {
	
	ArrayList<String> items;
	ArrayAdapter<String> itemsAdapter;
	ListView lvItems;
	
	private final int REQUEST_CODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_todo);
        items = new ArrayList<String>();
        
        readItems();
        
        lvItems = (ListView) findViewById(R.id.lvItems);
        itemsAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, items);
        lvItems.setAdapter(itemsAdapter);
        
        setUpListViewListener();
    }


    private void setUpListViewListener() {
    	lvItems.setOnItemLongClickListener(new OnItemLongClickListener() {

			@Override
			public boolean onItemLongClick(AdapterView<?> aView, View item,
					int pos, long id) {
				items.remove(pos);
				itemsAdapter.notifyDataSetChanged();
				saveItems();
				return true;
			}
		});
    	
    	lvItems.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> aView, View item, int pos,
					long id) {
				Intent editIntent = new Intent(TodoActivity.this, EditItemActivity.class);
				editIntent.putExtra(TodoConstants.EDIT_ITEM, items.get(pos).toString());
				editIntent.putExtra(TodoConstants.EDIT_ITEM_POS, pos);
				startActivityForResult(editIntent, REQUEST_CODE);
			}
		});
	}

	@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.todo, menu);
        return true;
    }
    
    public void addTodoItem(View v){
    	EditText etNewItem = (EditText) findViewById(R.id.etNewItem);
    	itemsAdapter.add(etNewItem.getText().toString());
    	etNewItem.setText("");
    }
    
    private void readItems(){
    	
    	File fileDir = getFilesDir();
    	File todoFile = new File(fileDir, TodoConstants.TODO_FILE);
    	try{
    		items = new ArrayList<String>(FileUtils.readLines(todoFile));
    	}catch(IOException e){
    		items = new ArrayList<String>();
    		e.printStackTrace();
    	}
    }
    
    private void saveItems(){
    	File fileDir = getFilesDir();
    	File todoFile = new File(fileDir, TodoConstants.TODO_FILE);
    	try{
    		FileUtils.writeLines(todoFile, items);
    	}catch(IOException e){
    		e.printStackTrace();
    	}
    }
    
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
      // REQUEST_CODE is defined above
      if (resultCode == RESULT_OK && requestCode == REQUEST_CODE) {
         String editedItem = data.getExtras().getString(TodoConstants.EDITED_ITEM);
         int itemPosition = data.getExtras().getInt(TodoConstants.EDIT_ITEM_POS);
         items.remove(itemPosition);
         itemsAdapter.notifyDataSetChanged();
         items.add(itemPosition, editedItem);
		 saveItems();
      }
    } 
    
}
