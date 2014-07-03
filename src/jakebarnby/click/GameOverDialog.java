package jakebarnby.click;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.app.DialogFragment;

public class GameOverDialog extends DialogFragment {
	
	private int totalClicks;
	
	@Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the Builder class for convenient dialog construction
		totalClicks = getArguments().getInt("clickCount");
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(R.string.dialog_game_over)
        	   .setMessage("Your score: " + totalClicks + "\n" + "Clicks per second: " + totalClicks/15)
               .setPositiveButton(R.string.button_newgame, new DialogInterface.OnClickListener() {
                   public void onClick(DialogInterface dialog, int id) {
                       
                   }
               })
               .setNegativeButton(R.string.quit, new DialogInterface.OnClickListener() {
                   public void onClick(DialogInterface dialog, int id) {
                       //current.finish();          
                      // current.moveTaskToBack(true);
                   }
               });
        // Create the AlertDialog object and return it
        return builder.create();
    }
}
