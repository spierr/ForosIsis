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

package co.edu.uniandes.csw.G3xtreme.coordinador.master.logic.ejb;

import co.edu.uniandes.csw.G3xtreme.tarea.logic.dto.TareaDTO;
import co.edu.uniandes.csw.G3xtreme.tarea.persistence.api.ITareaPersistence;
import co.edu.uniandes.csw.G3xtreme.coordinador.logic.dto.CoordinadorDTO;
import co.edu.uniandes.csw.G3xtreme.coordinador.master.logic.api._ICoordinadorMasterLogicService;
import co.edu.uniandes.csw.G3xtreme.coordinador.master.logic.dto.CoordinadorMasterDTO;
import co.edu.uniandes.csw.G3xtreme.coordinador.master.persistence.api.ICoordinadorMasterPersistence;
import co.edu.uniandes.csw.G3xtreme.coordinador.master.persistence.entity.Coordinadortarea_coordinadorEntity;
import co.edu.uniandes.csw.G3xtreme.coordinador.persistence.api.ICoordinadorPersistence;
import javax.inject.Inject;

public abstract class _CoordinadorMasterLogicService implements _ICoordinadorMasterLogicService {

    @Inject
    protected ICoordinadorPersistence coordinadorPersistance;
    @Inject
    protected ICoordinadorMasterPersistence coordinadorMasterPersistance;
    @Inject
    protected ITareaPersistence tareaPersistance;

    public CoordinadorMasterDTO createMasterCoordinador(CoordinadorMasterDTO coordinador) {
        CoordinadorDTO persistedCoordinadorDTO = coordinadorPersistance.createCoordinador(coordinador.getCoordinadorEntity());
        if (coordinador.getCreatetarea_coordinador() != null) {
            for (TareaDTO tareaDTO : coordinador.getCreatetarea_coordinador()) {
                TareaDTO createdTareaDTO = tareaPersistance.createTarea(tareaDTO);
                Coordinadortarea_coordinadorEntity coordinadorTareaEntity = new Coordinadortarea_coordinadorEntity(persistedCoordinadorDTO.getId(), createdTareaDTO.getId());
                coordinadorMasterPersistance.createCoordinadortarea_coordinadorEntity(coordinadorTareaEntity);
            }
        }
        // update tarea
        if (coordinador.getUpdatetarea_coordinador() != null) {
            for (TareaDTO tareaDTO : coordinador.getUpdatetarea_coordinador()) {
                tareaPersistance.updateTarea(tareaDTO);
                Coordinadortarea_coordinadorEntity coordinadorTareaEntity = new Coordinadortarea_coordinadorEntity(persistedCoordinadorDTO.getId(), tareaDTO.getId());
                coordinadorMasterPersistance.createCoordinadortarea_coordinadorEntity(coordinadorTareaEntity);
            }
        }
        return coordinador;
    }

    public CoordinadorMasterDTO getMasterCoordinador(Long id) {
        return coordinadorMasterPersistance.getCoordinador(id);
    }

    public void deleteMasterCoordinador(Long id) {
        coordinadorPersistance.deleteCoordinador(id);
    }

    public void updateMasterCoordinador(CoordinadorMasterDTO coordinador) {
        coordinadorPersistance.updateCoordinador(coordinador.getCoordinadorEntity());

        //---- FOR RELATIONSHIP
        // delete tarea
        if (coordinador.getDeletetarea_coordinador() != null) {
            for (TareaDTO tareaDTO : coordinador.getDeletetarea_coordinador()) {
                coordinadorMasterPersistance.deleteCoordinadortarea_coordinadorEntity(coordinador.getCoordinadorEntity().getId(), tareaDTO.getId());
            }
        }
        // persist new tarea
        if (coordinador.getCreatetarea_coordinador() != null) {
            for (TareaDTO tareaDTO : coordinador.getCreatetarea_coordinador()) {
                Coordinadortarea_coordinadorEntity coordinadorTareaEntity = new Coordinadortarea_coordinadorEntity(coordinador.getCoordinadorEntity().getId(), tareaDTO.getId());
                coordinadorMasterPersistance.createCoordinadortarea_coordinadorEntity(coordinadorTareaEntity);
            }
        }
        // update tarea
        if (coordinador.getUpdatetarea_coordinador() != null) {
            for (TareaDTO tareaDTO : coordinador.getUpdatetarea_coordinador()) {
                coordinadorMasterPersistance.deleteCoordinadortarea_coordinadorEntity(coordinador.getCoordinadorEntity().getId(), tareaDTO.getId());
                tareaPersistance.updateTarea(tareaDTO);
                Coordinadortarea_coordinadorEntity coordinadorTareaEntity = new Coordinadortarea_coordinadorEntity(coordinador.getId(), tareaDTO.getId());
                coordinadorMasterPersistance.createCoordinadortarea_coordinadorEntity(coordinadorTareaEntity);
                
            }
        }
    }
}
