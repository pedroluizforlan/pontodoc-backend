package com.pedroluizforlan.pontodoc.service.imp;

import java.util.List;

import org.springframework.stereotype.Service;

import com.pedroluizforlan.pontodoc.repository.DriveIntegrationRepository;
import com.pedroluizforlan.pontodoc.service.DriveIntegrationService;
import com.pedroluizforlan.pontodoc.model.DriveIntegration;

@Service
public class DriveIntegrationImp implements DriveIntegrationService {

    private DriveIntegrationRepository driveIntegrationRepository;

    public DriveIntegrationImp(DriveIntegrationRepository driveIntegrationRepository){
        this.driveIntegrationRepository = driveIntegrationRepository;
    }
    

    @Override
    public List<DriveIntegration> findAll() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'findAll'");
    }

    @Override
    public DriveIntegration findById(Long id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'findById'");
    }

    @Override
    public DriveIntegration create(DriveIntegration entity) {
        return driveIntegrationRepository.save(entity);
    }

    @Override
    public DriveIntegration update(Long id, DriveIntegration entity) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'update'");
    }

    @Override
    public DriveIntegration delete(Long id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'delete'");
    }

    
}
