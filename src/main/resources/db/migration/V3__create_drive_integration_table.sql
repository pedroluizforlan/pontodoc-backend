DROP TABLE IF EXISTS drive_integration CASCADE;

CREATE TABLE drive_integration (
    id BIGSERIAL PRIMARY KEY,               
    collaborator_id BIGINT NOT NULL,                                  
    folder_id INT,                                                                  
    CONSTRAINT fk_collaborator FOREIGN KEY (collaborator_id)    
        REFERENCES collaborators(id) ON DELETE CASCADE             
);