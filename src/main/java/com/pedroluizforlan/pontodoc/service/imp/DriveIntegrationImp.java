package com.pedroluizforlan.pontodoc.service.imp;

import java.util.List;

import org.springframework.stereotype.Service;

import com.pedroluizforlan.pontodoc.repository.DriveIntegrationRepository;
import com.pedroluizforlan.pontodoc.service.DriveIntegration;

@Service
public class DriveIntegrationImp implements DriveIntegration {

    private DriveIntegrationRepository driveIntegrationRepository;

    public DriveIntegrationImp(DriveIntegrationRepository driveIntegrationRepository){
        this.driveIntegrationRepository = driveIntegrationRepository;
    }

    @Override
    public List<Long> findAll() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'findAll'");
    }

    @Override
    public Long findById(DriveIntegration id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'findById'");
    }

    @Override
    public Long create(Long entity) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'create'");
    }

    @Override
    public Long update(DriveIntegration id, Long entity) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'update'");
    }

    @Override
    public Long delete(DriveIntegration id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'delete'");
    }
    
}
