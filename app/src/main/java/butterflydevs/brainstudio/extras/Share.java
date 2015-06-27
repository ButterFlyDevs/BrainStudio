/*
        This program is free software: you can redistribute it and/or modify
        it under the terms of the GNU General Public License as published by
        the Free Software Foundation, either version 3 of the License, or
        (at your option) any later version.

        This program is distributed in the hope that it will be useful,
        but WITHOUT ANY WARRANTY; without even the implied warranty of
        MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
        GNU General Public License for more details.

        You should have received a copy of the GNU General Public License
        along with this program. If not, see <http://www.gnu.org/licenses/>.

        Copyright 2015 Jose A. Gonzalez Cervera
        Copyright 2015 Juan A. Fernández Sánchez
*/
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