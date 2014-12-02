/* ========================================================================
 * Copyright 2014 G3xtreme
 *
 * Licensed under the MIT, The MIT License (MIT)
 * Copyright (c) 2014 G3xtreme

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in
all copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
THE SOFTWARE.
 * ========================================================================


Source generated by CrudMaker version 1.0.0.201411201032

*/

package co.edu.uniandes.csw.G3xtreme.foro.service;

import co.edu.uniandes.csw.G3xtreme.fase.logic.dto.FasePageDTO;
import co.edu.uniandes.csw.G3xtreme.foro.logic.api.IForoLogicService;
import co.edu.uniandes.csw.G3xtreme.foro.logic.dto.ForoDTO;
import co.edu.uniandes.csw.G3xtreme.info.dto.InfoDTO;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.UriInfo;

@Path("/Foro")
@Stateless
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class ForoService extends _ForoService {

    @Inject
    protected IForoLogicService foroLogicService;
    
    @Context
    UriInfo uriInfo;
    
    @GET
    @Path("/Login")
    public InfoDTO hacerLogin(){
        MultivaluedMap<String, String> queryParams = uriInfo.getQueryParameters();

        System.out.println("Autenticando usuario...");
        String usuario = queryParams.getFirst("email");
        String contrasena = queryParams.getFirst("password");

        System.out.println(usuario + ":" + contrasena);
        InfoDTO resp = new InfoDTO();

        //TODO autenticar el usuario con la logica de la persistencia

        if(usuario.equals("misabel") && contrasena.equals("admin")){
            resp.setNumInt(0);
            resp.setExito("misabel");
        }else if(usuario.equals("f.otalora10") && contrasena.equals("1234")){
            
        }else{
            resp.setNumInt(-1);
        }

        return resp;
    }
    
    @GET
    @Path("/PedirForo")
    public ForoDTO consultarForo(){
        MultivaluedMap<String, String> queryParams = uriInfo.getQueryParameters();

        String identificador = queryParams.getFirst("id");
        long id = Long.parseLong(identificador);
        System.out.println("Pidiendo foro..." + id);

        return foroLogicService.getForo(id);
    }
}