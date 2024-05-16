package org.example.updateFrame;

import org.example.createFrame.FolderFrameCreate;
import org.example.entity.Folder;
import org.example.entity.Uniobject;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class FolderFrameUpdate extends UniObjectFrameUpdate{
    private Folder folder;

    public FolderFrameUpdate(Folder folder){
        super(folder);
        this.folder = folder;
        setTitle("Folder Detail");

    }

}
