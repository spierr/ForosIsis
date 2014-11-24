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

package co.edu.uniandes.csw.G3xtreme.estadisticas.logic.mock;
import java.util.ArrayList;
import java.util.List;

import co.edu.uniandes.csw.G3xtreme.estadisticas.logic.dto.EstadisticasDTO;
import co.edu.uniandes.csw.G3xtreme.estadisticas.logic.api.IEstadisticasLogicService;
import co.edu.uniandes.csw.G3xtreme.estadisticas.master.logic.api._IEstadisticasMasterLogicService;
import co.edu.uniandes.csw.G3xtreme.estadisticas.master.logic.dto.EstadisticasMasterDTO;
import co.edu.uniandes.csw.G3xtreme.empresa.logic.api.IEmpresaLogicService;
import co.edu.uniandes.csw.G3xtreme.dia_inscripcion.logic.api.IDia_InscripcionLogicService;
import co.edu.uniandes.csw.G3xtreme.empresa.logic.dto.EmpresaDTO;
import co.edu.uniandes.csw.G3xtreme.dia_inscripcion.logic.dto.Dia_InscripcionDTO;
import javax.inject.Inject;


public abstract class _EstadisticasMasterMockLogicService implements _IEstadisticasMasterLogicService {

    protected static ArrayList<EstadisticasMasterDTO> estadisticasMasterDtosList = new ArrayList<EstadisticasMasterDTO>() ;
    @Inject
    protected IEmpresaLogicService empresaPersistance;
    @Inject
    protected IDia_InscripcionLogicService dia_InscripcionPersistance;
    @Inject
    protected IEstadisticasLogicService estadisticasPersistance;

    public EstadisticasMasterDTO createMasterEstadisticas(EstadisticasMasterDTO estadisticas) {

        estadisticasPersistance.createEstadisticas(estadisticas.getEstadisticasEntity());
        for (Dia_InscripcionDTO dto : estadisticas.getCreatedia_Inscripcion()) {
            estadisticas.getListdia_Inscripcion().add(dia_InscripcionPersistance.createDia_Inscripcion(dto));
        }
        for (EmpresaDTO dto : estadisticas.getCreateempresa()) {
            estadisticas.getListempresa().add(empresaPersistance.createEmpresa(dto));
        }
        estadisticasMasterDtosList.add(estadisticas);
        return estadisticas;
    }

    public EstadisticasMasterDTO getMasterEstadisticas(Long id) {
        for (EstadisticasMasterDTO estadisticasMasterDTO : estadisticasMasterDtosList) {
            if (estadisticasMasterDTO.getEstadisticasEntity().getId() == id) {
                return estadisticasMasterDTO;
            }
        }

        return null;
    }

    public void deleteMasterEstadisticas(Long id) {
        for (EstadisticasMasterDTO estadisticasMasterDTO : estadisticasMasterDtosList) {
            if (estadisticasMasterDTO.getEstadisticasEntity().getId() == id) {

                for (Dia_InscripcionDTO dto : estadisticasMasterDTO.getCreatedia_Inscripcion()) {
                    dia_InscripcionPersistance.deleteDia_Inscripcion(dto.getId());
                }
                estadisticasPersistance.deleteEstadisticas(estadisticasMasterDTO.getId());
                estadisticasMasterDtosList.remove(estadisticasMasterDTO);
                for (EmpresaDTO dto : estadisticasMasterDTO.getCreateempresa()) {
                    empresaPersistance.deleteEmpresa(dto.getId());
                }
                estadisticasPersistance.deleteEstadisticas(estadisticasMasterDTO.getId());
                estadisticasMasterDtosList.remove(estadisticasMasterDTO);
            }
        }

    }

    public void updateMasterEstadisticas(EstadisticasMasterDTO estadisticas) {

		EstadisticasMasterDTO currentEstadisticas = getMasterEstadisticas(estadisticas.getEstadisticasEntity().getId());
		if (currentEstadisticas == null) {
			currentEstadisticas = estadisticas;
		}else{
			estadisticasMasterDtosList.remove(currentEstadisticas);
		}

        // update Dia_Inscripcion
        if (estadisticas.getUpdatedia_Inscripcion() != null) {
            for (Dia_InscripcionDTO dto : estadisticas.getUpdatedia_Inscripcion()) {
                dia_InscripcionPersistance.updateDia_Inscripcion(dto);
                for (Dia_InscripcionDTO dia_Inscripciondto : currentEstadisticas.getListdia_Inscripcion()) {
					if (dia_Inscripciondto.getId() == dto.getId()) {
						currentEstadisticas.getListdia_Inscripcion().remove(dia_Inscripciondto);
						currentEstadisticas.getListdia_Inscripcion().add(dto);
					}
				}
            }
        }
        // persist new Dia_Inscripcion
        if (estadisticas.getCreatedia_Inscripcion() != null) {
            for (Dia_InscripcionDTO dto : estadisticas.getCreatedia_Inscripcion()) {
                Dia_InscripcionDTO persistedDia_InscripcionDTO = dia_InscripcionPersistance.createDia_Inscripcion(dto);
                dto = persistedDia_InscripcionDTO;
                currentEstadisticas.getListdia_Inscripcion().add(dto);
            }
        }
        // delete Dia_Inscripcion
        if (estadisticas.getDeletedia_Inscripcion() != null) {
            for (Dia_InscripcionDTO dto : estadisticas.getDeletedia_Inscripcion()) {
				for (Dia_InscripcionDTO dia_Inscripciondto : currentEstadisticas.getListdia_Inscripcion()) {
					if (dia_Inscripciondto.getId() == dto.getId()) {
						currentEstadisticas.getListdia_Inscripcion().remove(dia_Inscripciondto);
					}
				}
                dia_InscripcionPersistance.deleteDia_Inscripcion(dto.getId());
            }
        }
        // update Empresa
        if (estadisticas.getUpdateempresa() != null) {
            for (EmpresaDTO dto : estadisticas.getUpdateempresa()) {
                empresaPersistance.updateEmpresa(dto);
                for (EmpresaDTO empresadto : currentEstadisticas.getListempresa()) {
					if (empresadto.getId() == dto.getId()) {
						currentEstadisticas.getListempresa().remove(empresadto);
						currentEstadisticas.getListempresa().add(dto);
					}
				}
            }
        }
        // persist new Empresa
        if (estadisticas.getCreateempresa() != null) {
            for (EmpresaDTO dto : estadisticas.getCreateempresa()) {
                EmpresaDTO persistedEmpresaDTO = empresaPersistance.createEmpresa(dto);
                dto = persistedEmpresaDTO;
                currentEstadisticas.getListempresa().add(dto);
            }
        }
        // delete Empresa
        if (estadisticas.getDeleteempresa() != null) {
            for (EmpresaDTO dto : estadisticas.getDeleteempresa()) {
				for (EmpresaDTO empresadto : currentEstadisticas.getListempresa()) {
					if (empresadto.getId() == dto.getId()) {
						currentEstadisticas.getListempresa().remove(empresadto);
					}
				}
                empresaPersistance.deleteEmpresa(dto.getId());
            }
        }
        estadisticasMasterDtosList.add(currentEstadisticas);
    }
}