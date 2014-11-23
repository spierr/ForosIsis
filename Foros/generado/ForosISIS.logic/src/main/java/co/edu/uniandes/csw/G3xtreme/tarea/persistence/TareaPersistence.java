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


Source generated by CrudMaker version 1.0.0.201410152247

*/

package co.edu.uniandes.csw.G3xtreme.tarea.persistence;

import co.edu.uniandes.csw.G3xtreme.info.logic.dto.InfoDTO;
import co.edu.uniandes.csw.G3xtreme.tarea.logic.dto.TareaDTO;
import co.edu.uniandes.csw.G3xtreme.tarea.logic.dto.TareaPageDTO;
import co.edu.uniandes.csw.G3xtreme.tarea.persistence.api.ITareaPersistence;
import co.edu.uniandes.csw.G3xtreme.tarea.persistence.converter.TareaConverter;
import java.util.Iterator;
import java.util.List;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.enterprise.inject.Default;
import javax.persistence.Query;

@Default
@Stateless 
@LocalBean
public class TareaPersistence extends _TareaPersistence  implements ITareaPersistence {

    public TareaPageDTO getTareasAll(String responsable) {
        Query count = entityManager.createQuery("select count(u) from TareaEntity u");
        Long regCount = 0L;
        regCount = Long.parseLong(count.getSingleResult().toString());
        Query q = entityManager.createQuery("SELECT all FROM TareaEntity");
        //q.setParameter("responsable", "%"+responsable+"%");

        TareaPageDTO response = new TareaPageDTO();
        response.setTotalRecords(regCount);
        response.setRecords(TareaConverter.entity2PersistenceDTOList(q.getResultList()));
        return response;
    }

    public TareaPageDTO getTareasResponsable(String responsable) {
    Query count = entityManager.createQuery("select count(u) from TAREAENTITY u");
        Long regCount = 0L;
        regCount = Long.parseLong(count.getSingleResult().toString());
        Query q = entityManager.createQuery("SELECT u FROM TAREAENTITY u WHERE u.responsable like :responsable");
        q.setParameter("responsable", "%"+responsable+"%");

        TareaPageDTO response = new TareaPageDTO();
        response.setTotalRecords(regCount);
        response.setRecords(TareaConverter.entity2PersistenceDTOList(q.getResultList()));
        return response;
    }

    public InfoDTO updateEstadoTarea(long id, String idTarea, String valor)
    {
        Query q = entityManager.createQuery("SELECT u FROM TAREAENTITY u WHERE u.idTarea like :idTarea");
        q.setParameter("idTarea", "%"+idTarea+"%");
        InfoDTO r= new InfoDTO();
        r.setExito("Se pudo");
        return r;
    }
    
    public TareaPageDTO getTareasByFase(String idFase) {
    
          Query q = entityManager.createQuery("select a, b from  FASETAREA_FASEENTITY as a, TAREAENTITY as b "+
           "where  b.ID = a.TAREA_FASEID and a.TAREA_FASEID = :idFase");
           q.setParameter("idFase", "%"+idFase+"%");
           List r= q.getResultList();
          
         TareaPageDTO response = new TareaPageDTO();
         response.setRecords(TareaConverter.entity2PersistenceDTOList(r));
         int count =0;
         for (Iterator iterator = r.iterator(); iterator.hasNext();) {
            iterator.next();
            count++;
        }
         response.setTotalRecords((long)count);
         
         return response;
    }

    public TareaDTO creartarea(TareaDTO nueva, String idFase) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }



    }


