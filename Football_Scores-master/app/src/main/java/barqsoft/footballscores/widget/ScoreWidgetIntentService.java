package barqsoft.footballscores.widget;

import android.app.IntentService;
import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.util.Log;
import android.widget.RemoteViews;

import barqsoft.footballscores.database.DatabaseContract;
import barqsoft.footballscores.MainActivity;
import barqsoft.footballscores.R;
import barqsoft.footballscores.Utilies;

public class ScoreWidgetIntentService extends IntentService implements LoaderManager.LoaderCallbacks<Cursor> {
    private static final String[] SCORE_COLUMNS = {
            DatabaseContract.scores_table.DATE_COL,
            DatabaseContract.scores_table.HOME_COL,
            DatabaseContract.scores_table.AWAY_COL,
            DatabaseContract.scores_table.HOME_GOALS_COL,
            DatabaseContract.scores_table.AWAY_GOALS_COL,
            DatabaseContract.scores_table.MATCH_ID,
            DatabaseContract.scores_table.LEAGUE_COL
    };

    public static final int COL_DATE = 0;
    public static final int COL_HOME = 1;
    public static final int COL_AWAY = 2;
    public static final int COL_HOME_GOALS = 3;
    public static final int COL_AWAY_GOALS = 4;
    public static final int MATCH_ID = 5;
    public static final int COL_LEAGUE = 6;

    public ScoreWidgetIntentService() {
        super("ScoreWidgetIntentService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(this);
        int[] appWidgetIds = appWidgetManager
                .getAppWidgetIds(new ComponentName(this, ScoreAppWidget.class));

        Log.i("INFO", "APPWIDGET SIZE: " + appWidgetIds.length);

        Uri dateUri = DatabaseContract.scores_table.buildScoreWithDate();
        // Log.i("INFO", "DATE URI: " + dateUri.toString());
        String[] date = new String[1];
        date[0] = Utilies.getFragmentDate(0);
        Cursor data = getContentResolver().query(dateUri, SCORE_COLUMNS, "date", date, null);
        // Cursor data = getContentResolver().query(dateUri, SCORE_COLUMNS, "date", null, null);

        /*
        Uri idUri = DatabaseContract.scores_table.buildScoreWithId();
        Log.i("INFO", "ID URI: " + idUri.toString());
        String[] id = new String[1];
        id[0] = "140514";
        Cursor data = getContentResolver().query(idUri, SCORE_COLUMNS, "id", id, null);
        */

        /*
        Uri leagueUri = DatabaseContract.scores_table.buildScoreWithLeague();
        // Log.i("INFO", "DATE URI: " + dateUri.toString());
        String[] leagueN = new String[1];
        leagueN[0] = "395";
        //Cursor data = getContentResolver().query(dateUri, SCORE_COLUMNS, "date", date, null);
        Cursor data = getContentResolver().query(leagueUri, SCORE_COLUMNS, null, leagueN, null);
        */

        // Uri uri = DatabaseContract.scores_table.buildScore();
        // Log.i("INFO", "DATE URI: " + dateUri.toString());
        //String[] leagueN = new String[1];
        //leagueN[0] = "395";
        //Cursor data = getContentResolver().query(dateUri, SCORE_COLUMNS, "date", date, null);
        // Cursor data = getContentResolver().query(uri, SCORE_COLUMNS, null, null, null);

        if (data == null) {
            return;
        }

        if (!data.moveToFirst()) {
            data.close();
            return;
        }

        String homeName = data.getString(COL_HOME);
        String awayName = data.getString(COL_AWAY);
        int homeScore = data.getInt(COL_HOME_GOALS);
        int awayScore = data.getInt(COL_AWAY_GOALS);
        int matchId = data.getInt(MATCH_ID);
        String league = data.getString(COL_LEAGUE);

        data.close();

        Log.i("INFO", "PASSED PASSED PASSED");
        Log.i("INFO", "MATCH ID: " + matchId);
        // There may be multiple widgets active, so update all of them

        for (int appWidgetId : appWidgetIds) {

            // Construct the RemoteViews object
            RemoteViews views = new RemoteViews(getPackageName(), R.layout.score_app_widget);
            Log.i("INFO", "HOME NAME " + homeName);
            Log.i("INFO", "AWAY NAME " + awayName);
            Log.i("INFO", "SCORES " + Utilies.getScores(homeScore, awayScore));

            views.setTextViewText(R.id.home_name, homeName);
            views.setTextViewText(R.id.away_name, awayName);
            views.setTextViewText(R.id.score_textview, Utilies.getScores(homeScore, awayScore));
            // views.setTextViewText(R.id.home_score, "" + homeScore);
            // views.setTextViewText(R.id.away_score, "" + awayScore);

            // Led to the MainActivity
            Intent launchIntent = new Intent(this, MainActivity.class);
            PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, launchIntent, 0);
            views.setOnClickPendingIntent(R.id.widget, pendingIntent);

            // Instruct the widget manager to update the widget
            appWidgetManager.updateAppWidget(appWidgetId, views);
        }
    }

    @Override
    public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
        return null;
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {

    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }
}
