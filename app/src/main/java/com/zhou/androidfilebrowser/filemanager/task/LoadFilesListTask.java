package com.zhou.androidfilebrowser.filemanager.task;

import android.os.AsyncTask;

import com.zhou.androidfilebrowser.filemanager.FileBean;
import com.zhou.androidfilebrowser.filemanager.FileHelper;

import java.util.ArrayList;
import java.util.List;

public class LoadFilesListTask extends AsyncTask<Void, Void, List<FileBean>> {
    public String path;
    private LoadFilesListListener loadFilesListListener;

    public LoadFilesListTask(String path) {
        this.path = path;
    }

    @Override
    protected List<FileBean> doInBackground(Void... voids) {
        List<FileBean> fileBeans = FileHelper.getFiles(path, true);

        return fileBeans;
    }

    @Override
    protected void onPostExecute(List<FileBean> fileBeans) {
        super.onPostExecute(fileBeans);
        if (loadFilesListListener != null) loadFilesListListener.onAsyncTaskFinished(fileBeans);
    }

    public void setLoadFilesListListener(LoadFilesListListener loadFilesListListener) {
        this.loadFilesListListener = loadFilesListListener;
    }

    public interface LoadFilesListListener {
        void onAsyncTaskFinished(List<FileBean> list);
    }
}
