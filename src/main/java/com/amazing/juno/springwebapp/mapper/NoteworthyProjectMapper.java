package com.amazing.juno.springwebapp.mapper;


import com.amazing.juno.springwebapp.dto.NoteworthyProjectDTO;
import com.amazing.juno.springwebapp.entity.NoteworthyProject;
import org.mapstruct.Mapper;

@Mapper
public interface NoteworthyProjectMapper {

    NoteworthyProjectDTO noteworthyProjectToNoteworthyProjectDTO(NoteworthyProject noteworthyProject);

    NoteworthyProject noteworthyProjectDTOToNoteworthyProject(NoteworthyProjectDTO noteworthyProjectDTO);
}
