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

package co.edu.uniandes.csw.G3xtreme.estadisticas.master.logic.ejb;

import co.edu.uniandes.csw.G3xtreme.dia_inscripcion.logic.dto.Dia_InscripcionDTO;
import co.edu.uniandes.csw.G3xtreme.dia_inscripcion.persistence.api.IDia_InscripcionPersistence;
import co.edu.uniandes.csw.G3xtreme.empresa.logic.dto.EmpresaDTO;
import co.edu.uniandes.csw.G3xtreme.empresa.persistence.api.IEmpresaPersistence;
import co.edu.uniandes.csw.G3xtreme.estadisticas.logic.dto.EstadisticasDTO;
import co.edu.uniandes.csw.G3xtreme.estadisticas.master.logic.api._IEstadisticasMasterLogicService;
import co.edu.uniandes.csw.G3xtreme.estadisticas.master.logic.dto.EstadisticasMasterDTO;
import co.edu.uniandes.csw.G3xtreme.estadisticas.master.persistence.api.IEstadisticasMasterPersistence;
import co.edu.uniandes.csw.G3xtreme.estadisticas.master.persistence.entity.Estadisticasdia_InscripcionEntity;
import co.edu.uniandes.csw.G3xtreme.estadisticas.master.persistence.entity.EstadisticasempresaEntity;
import co.edu.uniandes.csw.G3xtreme.estadisticas.persistence.api.IEstadisticasPersistence;
import javax.inject.Inject;

public abstract class _EstadisticasMasterLogicService implements _IEstadisticasMasterLogicService {

    @Inject
    protected IEstadisticasPersistence estadisticasPersistance;
    @Inject
    protected IEstadisticasMasterPersistence estadisticasMasterPersistance;
    @Inject
    protected IEmpresaPersistence empresaPersistance;
    @Inject
    protected IDia_InscripcionPersistence dia_InscripcionPersistance;

    public EstadisticasMasterDTO createMasterEstadisticas(EstadisticasMasterDTO estadisticas) {
        EstadisticasDTO persistedEstadisticasDTO = estadisticasPersistance.createEstadisticas(estadisticas.getEstadisticasEntity());
        if (estadisticas.getCreatedia_Inscripcion() != null) {
            for (Dia_InscripcionDTO dia_InscripcionDTO : estadisticas.getCreatedia_Inscripcion()) {
                Dia_InscripcionDTO createdDia_InscripcionDTO = dia_InscripcionPersistance.createDia_Inscripcion(dia_InscripcionDTO);
                Estadisticasdia_InscripcionEntity estadisticasDia_InscripcionEntity = new Estadisticasdia_InscripcionEntity(persistedEstadisticasDTO.getId(), createdDia_InscripcionDTO.getId());
                estadisticasMasterPersistance.createEstadisticasdia_InscripcionEntity(estadisticasDia_InscripcionEntity);
            }
        }
        if (estadisticas.getCreateempresa() != null) {
            for (EmpresaDTO empresaDTO : estadisticas.getCreateempresa()) {
                EmpresaDTO createdEmpresaDTO = empresaPersistance.createEmpresa(empresaDTO);
                EstadisticasempresaEntity estadisticasEmpresaEntity = new EstadisticasempresaEntity(persistedEstadisticasDTO.getId(), createdEmpresaDTO.getId());
                estadisticasMasterPersistance.createEstadisticasempresaEntity(estadisticasEmpresaEntity);
            }
        }
        // update dia_Inscripcion
        if (estadisticas.getUpdatedia_Inscripcion() != null) {
            for (Dia_InscripcionDTO dia_InscripcionDTO : estadisticas.getUpdatedia_Inscripcion()) {
                dia_InscripcionPersistance.updateDia_Inscripcion(dia_InscripcionDTO);
                Estadisticasdia_InscripcionEntity estadisticasDia_InscripcionEntity = new Estadisticasdia_InscripcionEntity(persistedEstadisticasDTO.getId(), dia_InscripcionDTO.getId());
                estadisticasMasterPersistance.createEstadisticasdia_InscripcionEntity(estadisticasDia_InscripcionEntity);
            }
        }
        // update empresa
        if (estadisticas.getUpdateempresa() != null) {
            for (EmpresaDTO empresaDTO : estadisticas.getUpdateempresa()) {
                empresaPersistance.updateEmpresa(empresaDTO);
                EstadisticasempresaEntity estadisticasEmpresaEntity = new EstadisticasempresaEntity(persistedEstadisticasDTO.getId(), empresaDTO.getId());
                estadisticasMasterPersistance.createEstadisticasempresaEntity(estadisticasEmpresaEntity);
            }
        }
        return estadisticas;
    }

    public EstadisticasMasterDTO getMasterEstadisticas(Long id) {
        return estadisticasMasterPersistance.getEstadisticas(id);
    }

    public void deleteMasterEstadisticas(Long id) {
        estadisticasPersistance.deleteEstadisticas(id);
    }

    public void updateMasterEstadisticas(EstadisticasMasterDTO estadisticas) {
        estadisticasPersistance.updateEstadisticas(estadisticas.getEstadisticasEntity());

        //---- FOR RELATIONSHIP
        // persist new dia_Inscripcion
        if (estadisticas.getCreatedia_Inscripcion() != null) {
            for (Dia_InscripcionDTO dia_InscripcionDTO : estadisticas.getCreatedia_Inscripcion()) {
                Dia_InscripcionDTO createdDia_InscripcionDTO = dia_InscripcionPersistance.createDia_Inscripcion(dia_InscripcionDTO);
                Estadisticasdia_InscripcionEntity estadisticasDia_InscripcionEntity = new Estadisticasdia_InscripcionEntity(estadisticas.getEstadisticasEntity().getId(), createdDia_InscripcionDTO.getId());
                estadisticasMasterPersistance.createEstadisticasdia_InscripcionEntity(estadisticasDia_InscripcionEntity);
            }
        }
        // update dia_Inscripcion
        if (estadisticas.getUpdatedia_Inscripcion() != null) {
            for (Dia_InscripcionDTO dia_InscripcionDTO : estadisticas.getUpdatedia_Inscripcion()) {
                dia_InscripcionPersistance.updateDia_Inscripcion(dia_InscripcionDTO);
            }
        }
        // delete dia_Inscripcion
        if (estadisticas.getDeletedia_Inscripcion() != null) {
            for (Dia_InscripcionDTO dia_InscripcionDTO : estadisticas.getDeletedia_Inscripcion()) {
                estadisticasMasterPersistance.deleteEstadisticasdia_InscripcionEntity(estadisticas.getEstadisticasEntity().getId(), dia_InscripcionDTO.getId());
                dia_InscripcionPersistance.deleteDia_Inscripcion(dia_InscripcionDTO.getId());
            }
        }
        // delete empresa
        if (estadisticas.getDeleteempresa() != null) {
            for (EmpresaDTO empresaDTO : estadisticas.getDeleteempresa()) {
                estadisticasMasterPersistance.deleteEstadisticasempresaEntity(estadisticas.getEstadisticasEntity().getId(), empresaDTO.getId());
            }
        }
        // persist new empresa
        if (estadisticas.getCreateempresa() != null) {
            for (EmpresaDTO empresaDTO : estadisticas.getCreateempresa()) {
                EstadisticasempresaEntity estadisticasEmpresaEntity = new EstadisticasempresaEntity(estadisticas.getEstadisticasEntity().getId(), empresaDTO.getId());
                estadisticasMasterPersistance.createEstadisticasempresaEntity(estadisticasEmpresaEntity);
            }
        }
        // update empresa
        if (estadisticas.getUpdateempresa() != null) {
            for (EmpresaDTO empresaDTO : estadisticas.getUpdateempresa()) {
                estadisticasMasterPersistance.deleteEstadisticasempresaEntity(estadisticas.getEstadisticasEntity().getId(), empresaDTO.getId());
                empresaPersistance.updateEmpresa(empresaDTO);
                EstadisticasempresaEntity estadisticasEmpresaEntity = new EstadisticasempresaEntity(estadisticas.getId(), empresaDTO.getId());
                estadisticasMasterPersistance.createEstadisticasempresaEntity(estadisticasEmpresaEntity);
                
            }
        }
    }
}
