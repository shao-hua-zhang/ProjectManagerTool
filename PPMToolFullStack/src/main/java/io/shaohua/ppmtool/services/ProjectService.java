package io.shaohua.ppmtool.services;

import io.shaohua.ppmtool.domain.Project;
import io.shaohua.ppmtool.exceptions.ProjectIdException;
import io.shaohua.ppmtool.repositories.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service  // most logic are put in the service layer, don't put in controller layer.
public class ProjectService {

    @Autowired
    private ProjectRepository projectRepository; // 在 service layer 控制自动连线问题， 而controller只要负责逻辑问题

    public Project saveOrUpdateProject(Project project) {
        // Logic handle duplicated id exception
        try {
            project.setProjectIdentifier(project.getProjectIdentifier().toUpperCase());
            return projectRepository.save(project);
        } catch (Exception e){
             throw new ProjectIdException("Project ID '" + project.getProjectIdentifier().toUpperCase() + "' already exists");
        }
    }

    public Project findProjectByIdentifier(String projectId) {
        Project project = projectRepository.findByProjectIdentifier(projectId.toUpperCase());
        if (project == null) {
            throw new ProjectIdException("Project ID '" + projectId + "' does not exist");
        }
        return project;
    }

    public Iterable<Project> findAllProjects() {
        return projectRepository.findAll();
    }

    public void deleteProjectByIdentifier(String projectId) {
        Project project = projectRepository.findByProjectIdentifier(projectId.toUpperCase());
        if (project == null) {
            throw new ProjectIdException("Cannot delete project with ID '" + projectId + "'. This project does not exist");
        }
        projectRepository.delete(project);
    }
}
