package codepath.apps.simpletodo;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;

public class EditItemActivity extends Activity {
	
	EditText editText;
	int itemPos;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_edit);
		String editItem = getIntent().getStringExtra(TodoConstants.EDIT_ITEM);
		itemPos = getIntent().getIntExtra(TodoConstants.EDIT_ITEM_POS, 0);
		
		editText = (EditText) findViewById(R.id.etEditItem);
		editText.setText(editItem);
		editText.setSelection(editText.getText().length());
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.edit, menu);
		return true;
	}
	
	public void saveItem(View v){
		EditText etNewItem = (EditText) findViewById(R.id.etEditItem);
		
		Intent data = new Intent();
		data.putExtra(TodoConstants.EDITED_ITEM, etNewItem.getText().toString());
		data.putExtra(TodoConstants.EDIT_ITEM_POS, itemPos);
		setResult(RESULT_OK, data);
		finish();
	}

}
