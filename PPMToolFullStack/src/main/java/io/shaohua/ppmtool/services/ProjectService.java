package io.shaohua.ppmtool.services;

import io.shaohua.ppmtool.domain.Project;
import io.shaohua.ppmtool.exceptions.ProjectIdException;
import io.shaohua.ppmtool.repositories.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProjectService {

    @Autowired
    private ProjectRepository projectRepository; // 在 service layer 控制自动连线问题， 而controller只要负责逻辑问题

    public Project saveOrUpdateProject(Project project) {
        // Logic
        try {
            project.setProjectIdentifier(project.getProjectIdentifier().toUpperCase());
            return projectRepository.save(project);
        } catch (Exception e){
             throw new ProjectIdException("Project ID '" + project.getProjectIdentifier().toUpperCase() + "' already exists");
        }

    }
}
