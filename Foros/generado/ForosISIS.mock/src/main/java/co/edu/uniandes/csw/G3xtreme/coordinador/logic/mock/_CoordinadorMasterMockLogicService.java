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

package co.edu.uniandes.csw.G3xtreme.coordinador.logic.mock;
import java.util.ArrayList;
import java.util.List;

import co.edu.uniandes.csw.G3xtreme.coordinador.logic.dto.CoordinadorDTO;
import co.edu.uniandes.csw.G3xtreme.coordinador.logic.api.ICoordinadorLogicService;
import co.edu.uniandes.csw.G3xtreme.coordinador.master.logic.api._ICoordinadorMasterLogicService;
import co.edu.uniandes.csw.G3xtreme.coordinador.master.logic.dto.CoordinadorMasterDTO;
import co.edu.uniandes.csw.G3xtreme.tarea.logic.api.ITareaLogicService;
import co.edu.uniandes.csw.G3xtreme.tarea.logic.dto.TareaDTO;
import javax.inject.Inject;


public abstract class _CoordinadorMasterMockLogicService implements _ICoordinadorMasterLogicService {

    protected static ArrayList<CoordinadorMasterDTO> coordinadorMasterDtosList = new ArrayList<CoordinadorMasterDTO>() ;
    @Inject
    protected ITareaLogicService tareaPersistance;
    @Inject
    protected ICoordinadorLogicService coordinadorPersistance;

    public CoordinadorMasterDTO createMasterCoordinador(CoordinadorMasterDTO coordinador) {

        coordinadorPersistance.createCoordinador(coordinador.getCoordinadorEntity());
        for (TareaDTO dto : coordinador.getCreatetarea_coordinador()) {
            tareaPersistance.createTarea(dto);
        }
        coordinadorMasterDtosList.add(coordinador);
        return coordinador;
    }

    public CoordinadorMasterDTO getMasterCoordinador(Long id) {
        for (CoordinadorMasterDTO coordinadorMasterDTO : coordinadorMasterDtosList) {
            if (coordinadorMasterDTO.getCoordinadorEntity().getId() == id) {
                return coordinadorMasterDTO;
            }
        }

        return null;
    }

    public void deleteMasterCoordinador(Long id) {
        for (CoordinadorMasterDTO coordinadorMasterDTO : coordinadorMasterDtosList) {
            if (coordinadorMasterDTO.getCoordinadorEntity().getId() == id) {

                for (TareaDTO dto : coordinadorMasterDTO.getCreatetarea_coordinador()) {
                    tareaPersistance.deleteTarea(dto.getId());
                }
                coordinadorPersistance.deleteCoordinador(coordinadorMasterDTO.getId());
                coordinadorMasterDtosList.remove(coordinadorMasterDTO);
            }
        }

    }

    public void updateMasterCoordinador(CoordinadorMasterDTO coordinador) {

        // update Tarea
        if (coordinador.getUpdatetarea_coordinador() != null) {
            for (TareaDTO dto : coordinador.getUpdatetarea_coordinador()) {
                tareaPersistance.updateTarea(dto);
            }
        }
        // persist new Tarea
        if (coordinador.getCreatetarea_coordinador() != null) {
            for (TareaDTO dto : coordinador.getCreatetarea_coordinador()) {
                TareaDTO persistedTareaDTO = tareaPersistance.createTarea(dto);
                dto = persistedTareaDTO;
            }
        }
        // delete Tarea
        if (coordinador.getDeletetarea_coordinador() != null) {
            for (TareaDTO dto : coordinador.getDeletetarea_coordinador()) {

                tareaPersistance.deleteTarea(dto.getId());
            }
        }
    }
}