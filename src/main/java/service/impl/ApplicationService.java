package com.example.misael.service.impl;

import com.example.misael.Dto.VarguezDTO;
import com.example.misael.dto.*;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public interface ApplicationService {

    VarguezDTO varguezHttp() throws IOException;

    VarguezDTO varguezPostHttp(VarguezDTO varguezDTO) throws IOException;

    void varguezMyFirstObject(VarguezDTO varguezDTO);

}
