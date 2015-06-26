package khpi.iip.mipk.kharkiv.edu.dao.listeners;

import khpi.iip.mipk.kharkiv.edu.domain.PersistentEntity;
import org.hibernate.event.SaveOrUpdateEvent;
import org.hibernate.event.def.DefaultSaveEventListener;
import org.hibernate.event.def.DefaultUpdateEventListener;

import java.util.Date;

public class UpdateDateListener extends DefaultUpdateEventListener {

    @Override
    public void onSaveOrUpdate(SaveOrUpdateEvent event) {
        if (event.getObject() instanceof PersistentEntity) {
            PersistentEntity persistentEntity = (PersistentEntity) event.getObject();
            Date updated = new Date();
            persistentEntity.setUpdated(updated);
        }
        super.onSaveOrUpdate(event);
    }
}

