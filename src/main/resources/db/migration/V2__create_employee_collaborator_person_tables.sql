CREATE TABLE employee (
    id BIGSERIAL PRIMARY KEY,               
    job_title VARCHAR(255),                              
    department VARCHAR(255),                             
    hiring_date DATE,                               
    manager_id BIGINT,                                  
    leadership BOOLEAN,                                  
    created_at TIMESTAMP,                                
    uploaded_at TIMESTAMP,                               
    deleted_at TIMESTAMP,                                
    CONSTRAINT fk_manager FOREIGN KEY (manager_id)      
        REFERENCES employee(id) ON DELETE SET NULL      
);

CREATE TABLE person (
    id BIGSERIAL PRIMARY KEY,               
    name VARCHAR(255) NOT NULL,                          
    birthday DATE NOT NULL,                         
    cpf VARCHAR(11) NOT NULL UNIQUE,                     
    address VARCHAR(255),                                
    cep VARCHAR(8) NOT NULL,                            
    gender VARCHAR(10),                                  
    cellphone_number VARCHAR(11) NOT NULL UNIQUE,        
    created_at TIMESTAMP,                                 
    uploaded_at TIMESTAMP,                                
    deleted_at TIMESTAMP                                  
);

CREATE TABLE collaborator (
    id BIGSERIAL PRIMARY KEY,               
    employee_id BIGINT,                                  
    user_id BIGINT,                                     
    person_id BIGINT,                                    
    created_at TIMESTAMP,                                
    uploaded_at TIMESTAMP,                                
    deleted_at TIMESTAMP,                                
    CONSTRAINT fk_employee FOREIGN KEY (employee_id)    
        REFERENCES employee(id) ON DELETE CASCADE,       
    CONSTRAINT fk_user FOREIGN KEY (user_id)            
        REFERENCES user(id) ON DELETE CASCADE,          
    CONSTRAINT fk_person FOREIGN KEY (person_id)        
        REFERENCES person(id) ON DELETE CASCADE          
);
