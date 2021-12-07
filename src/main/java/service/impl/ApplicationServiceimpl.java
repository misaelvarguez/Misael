package com.example.misael.serviceimpl;

import com.example.misael.Dto.VarguezDTO;
import com.example.misael.Repository.UserRepository;
import com.example.misael.dto.*;
import com.example.misael.model.UserModel;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.apache.http.protocol.HttpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class ApplicationServiceimpl implements com.example.misael.service.impl.ApplicationService {

    @Autowired
    HttpService httpService;
    @Autowired
    UserRepository userRepository;

    @Override
    public VarguezDTO varguezHttp() throws IOException {
        JsonParser parser = new JsonParser();
        JsonObject json = (JsonObject) parser.parse(httpService.sendRequestHttpS("https://misael41.free.beeceptor.com", "GET", null, null, "json", null, null));
        VarguezDTO varguezDTO = new VarguezDTO();
        varguezDTO.setName(json.get("name").getAsString());
        varguezDTO.setId(json.get("id").getAsInt());
        varguezDTO.setStatus(json.get("status").getAsString());
        return varguezDTO;
    }

    @Override
    public VarguezDTO varguezPostHttp(VarguezDTO varguezDTO) throws IOException {
        JsonParser parser = new JsonParser();
        JsonObject json = (JsonObject) parser.parse(httpService.sendRequestHttpS("https://misael41.free.beeceptor.com/api/v1/varguezPost", "POST", null, null, "json", varguezDTO.toJson(), null));
        UserModel userModel = new UserModel();
        userModel.setName(varguezDTO.getMessage);
        userRepository.save(userModel);
        return varguezDTO;
    }

    @Override
    public void varguezMyFirstObject(VarguezDTO varguezDTO) {

    }
}