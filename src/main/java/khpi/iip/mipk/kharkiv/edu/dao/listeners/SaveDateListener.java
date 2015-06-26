package khpi.iip.mipk.kharkiv.edu.dao.listeners;

import khpi.iip.mipk.kharkiv.edu.domain.PersistentEntity;
import org.hibernate.event.SaveOrUpdateEvent;
import org.hibernate.event.def.DefaultSaveEventListener;

import java.util.Date;

public class SaveDateListener extends DefaultSaveEventListener {

    @Override
    public void onSaveOrUpdate(SaveOrUpdateEvent event) {
        if (event.getObject() instanceof PersistentEntity) {
            PersistentEntity persistentEntity = (PersistentEntity) event.getObject();
            Date created = new Date();
            persistentEntity.setCreated(created);
            persistentEntity.setUpdated(created);
        }
        super.onSaveOrUpdate(event);
    }
}

