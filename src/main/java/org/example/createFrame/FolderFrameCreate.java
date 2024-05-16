package org.example.createFrame;

import org.example.dao.UniobjectDao;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class FolderFrameCreate extends UniObjectFrameCreate{

    public FolderFrameCreate(Integer classId, Integer major){
        super(classId, major);

        super.panel.remove(0);
        super.panel.remove(0);
        super.panel.remove(2);
        super.panel.remove(2);
        super.saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try (PreparedStatement ps = jdbcService.getConnection().prepareStatement("INSERT into folders values (?)")) {
                    ps.setInt(1, UniobjectDao.getTheBiggestIdFromUniobj()+1);
                    ps.executeUpdate();
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });

        setTitle("Folder Detail");
    }


}
