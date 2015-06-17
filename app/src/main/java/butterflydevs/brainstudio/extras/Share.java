package butterflydevs.brainstudio.extras;

import android.content.Context;
import android.content.Intent;

import butterflydevs.brainstudio.R;

/**
 * Share a content using the user's installed apps
 *
 * Thanks to : http://labs.emich.be/2010/01/23/how-to-send-to-twitter-or-facebook-from-your-android-application/
 *
 * @author http://francho.org/lab/
 *
 */
public class Share {
    /**
     * Open a contextual Menu with the available applications to share
     *
     * @param ctx the Context (to open the menú and the new activity)
     * @param subject subject
     * @param text text
     */
    public static void share(Context ctx, String subject,String text) {
        final Intent intent = new Intent(Intent.ACTION_SEND);

        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_SUBJECT, subject);
        intent.putExtra(Intent.EXTRA_TEXT, text);

        ctx.startActivity(Intent.createChooser(intent, "yeah"));
    }
}