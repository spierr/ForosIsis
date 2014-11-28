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

package co.edu.uniandes.csw.G3xtreme.tarea.logic.ejb;

import co.edu.uniandes.csw.G3xtreme.fase.logic.api.IFaseLogicService;
import co.edu.uniandes.csw.G3xtreme.fase.logic.dto.FaseDTO;
import co.edu.uniandes.csw.G3xtreme.fase.master.persistence.api.IFaseMasterPersistence;
import co.edu.uniandes.csw.G3xtreme.fase.master.persistence.entity.Fasetarea_faseEntity;
import co.edu.uniandes.csw.G3xtreme.foro.logic.api.IForoLogicService;
import co.edu.uniandes.csw.G3xtreme.foro.logic.dto.ForoDTO;
import co.edu.uniandes.csw.G3xtreme.foro.master.logic.api.IForoMasterLogicService;
import co.edu.uniandes.csw.G3xtreme.foro.master.persistence.api.IForoMasterPersistence;
import co.edu.uniandes.csw.G3xtreme.foro.master.persistence.entity.Forofase_foroEntity;
import co.edu.uniandes.csw.G3xtreme.responsable.logic.api.IResponsableLogicService;
import co.edu.uniandes.csw.G3xtreme.responsable.logic.dto.ResponsableDTO;
import co.edu.uniandes.csw.G3xtreme.tarea.logic.api.ITareaLogicService;
import co.edu.uniandes.csw.G3xtreme.tarea.logic.dto.TareaDTO;
import co.edu.uniandes.csw.G3xtreme.tarea.logic.dto.TareaPageDTO;
import co.edu.uniandes.csw.G3xtreme.tarea.persistence.TareaPersistence;
import co.edu.uniandes.csw.G3xtreme.tarea.persistence.api.ITareaPersistence;
import co.edu.uniandes.csw.G3xtreme.tarea.persistence.converter.TareaConverter;
import co.edu.uniandes.csw.G3xtreme.tarea.persistence.entity.TareaEntity;
import static co.edu.uniandes.csw.G3xtreme.util._TestUtil.*;
import java.util.*;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.UserTransaction;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(Arquillian.class)
public class TareaLogicServiceTest {

	public static final String DEPLOY = "Prueba";

	@Deployment
	public static WebArchive createDeployment() {
		return ShrinkWrap.create(WebArchive.class, DEPLOY + ".war")
				.addPackage(TareaLogicService.class.getPackage())
				.addPackage(ITareaLogicService.class.getPackage())
				.addPackage(TareaPersistence.class.getPackage())
				.addPackage(TareaEntity.class.getPackage())
				.addPackage(ITareaPersistence.class.getPackage())
                .addPackage(TareaDTO.class.getPackage())
                .addPackage(TareaConverter.class.getPackage())
                .addPackage(TareaEntity.class.getPackage())
				.addAsResource("META-INF/persistence.xml", "META-INF/persistence.xml")
                .addAsWebInfResource("META-INF/beans.xml", "beans.xml");
	}

	@Inject
	private ITareaLogicService tareaLogicService;
	
	@Inject
	private ITareaPersistence tareaPersistence;
        
        @Inject
	private IFaseLogicService faseLogicService;
        
        @Inject
	private IForoLogicService foroLogicService;
        
        @Inject
	private IResponsableLogicService responsableLogicService;
        
        @Inject
	private IForoMasterLogicService foroMasterLogicService;
        
        @Inject
	private IForoMasterPersistence foroMasterPersistence;
        
        @Inject
	private IFaseMasterPersistence faseMasterPersistence;

	@Before
	public void configTest() {
		try {
			clearData();
			insertData();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void clearData() {
		List<TareaDTO> dtos=tareaPersistence.getTareas();
		for(TareaDTO dto:dtos){
			tareaPersistence.deleteTarea(dto.getId());
		}
	}

	private List<TareaDTO> data=new ArrayList<TareaDTO>();

	private void insertData() {
		for(int i=0;i<3;i++){
			TareaDTO pdto=new TareaDTO();
			pdto.setName(generateRandom(String.class));
			pdto.setResponsable(generateRandom(String.class));
			pdto.setFechaInicio(generateRandomDate());
			pdto.setFechaFin(generateRandomDate());
			pdto.setDescripcion(generateRandom(String.class));
			pdto.setEstado(generateRandom(Integer.class));
			pdto.setResponsable_tareaId(generateRandom(Long.class));
			pdto=tareaPersistence.createTarea(pdto);
			data.add(pdto);
		}
	}
	
	@Test
	public void createTareaTest(){
		TareaDTO ldto=new TareaDTO();
		ldto.setName(generateRandom(String.class));
		ldto.setResponsable(generateRandom(String.class));
		ldto.setFechaInicio(generateRandomDate());
		ldto.setFechaFin(generateRandomDate());
		ldto.setDescripcion(generateRandom(String.class));
		ldto.setEstado(generateRandom(Integer.class));
		ldto.setResponsable_tareaId(generateRandom(Long.class));
		
		
		TareaDTO result=tareaLogicService.createTarea(ldto);
		
		Assert.assertNotNull(result);
		
		TareaDTO pdto=tareaPersistence.getTarea(result.getId());
		
		Assert.assertEquals(ldto.getName(), pdto.getName());	
		Assert.assertEquals(ldto.getResponsable(), pdto.getResponsable());	
		Assert.assertEquals(ldto.getFechaInicio(), pdto.getFechaInicio());	
		Assert.assertEquals(ldto.getFechaFin(), pdto.getFechaFin());	
		Assert.assertEquals(ldto.getDescripcion(), pdto.getDescripcion());	
		Assert.assertEquals(ldto.getEstado(), pdto.getEstado());	
		Assert.assertEquals(ldto.getResponsable_tareaId(), pdto.getResponsable_tareaId());	
	}
	
	@Test
	public void getTareasTest(){
		List<TareaDTO> list=tareaLogicService.getTareas();
		Assert.assertEquals(list.size(), data.size());
        for(TareaDTO ldto:list){
        	boolean found=false;
            for(TareaDTO pdto:data){
            	if(ldto.getId()==pdto.getId()){
                	found=true;
                }
            }
            Assert.assertTrue(found);
        }
	}
	
	@Test
	public void getTareaTest(){
		TareaDTO pdto=data.get(0);
		TareaDTO ldto=tareaLogicService.getTarea(pdto.getId());
        Assert.assertNotNull(ldto);
		Assert.assertEquals(pdto.getName(), ldto.getName());
		Assert.assertEquals(pdto.getResponsable(), ldto.getResponsable());
		Assert.assertEquals(pdto.getFechaInicio(), ldto.getFechaInicio());
		Assert.assertEquals(pdto.getFechaFin(), ldto.getFechaFin());
		Assert.assertEquals(pdto.getDescripcion(), ldto.getDescripcion());
		Assert.assertEquals(pdto.getEstado(), ldto.getEstado());
		Assert.assertEquals(pdto.getResponsable_tareaId(), ldto.getResponsable_tareaId());
        
	}
	
	@Test
	public void deleteTareaTest(){
		TareaDTO pdto=data.get(0);
		tareaLogicService.deleteTarea(pdto.getId());
        TareaDTO deleted=tareaPersistence.getTarea(pdto.getId());
        Assert.assertNull(deleted);
	}
	
	@Test
	public void updateTareaTest(){
		TareaDTO pdto=data.get(0);
		
		TareaDTO ldto=new TareaDTO();
		ldto.setId(pdto.getId());
		ldto.setName(generateRandom(String.class));
		ldto.setResponsable(generateRandom(String.class));
		ldto.setFechaInicio(generateRandomDate());
		ldto.setFechaFin(generateRandomDate());
		ldto.setDescripcion(generateRandom(String.class));
		ldto.setEstado(generateRandom(Integer.class));
		ldto.setResponsable_tareaId(generateRandom(Long.class));
		
		
		tareaLogicService.updateTarea(ldto);
		
		
		TareaDTO resp=tareaPersistence.getTarea(pdto.getId());
		
		Assert.assertEquals(ldto.getName(), resp.getName());	
		Assert.assertEquals(ldto.getResponsable(), resp.getResponsable());	
		Assert.assertEquals(ldto.getFechaInicio(), resp.getFechaInicio());	
		Assert.assertEquals(ldto.getFechaFin(), resp.getFechaFin());	
		Assert.assertEquals(ldto.getDescripcion(), resp.getDescripcion());	
		Assert.assertEquals(ldto.getEstado(), resp.getEstado());	
		Assert.assertEquals(ldto.getResponsable_tareaId(), resp.getResponsable_tareaId());	
	}
	
	@Test
	public void getTareaPaginationTest(){
		
		TareaPageDTO dto1=tareaLogicService.getTareas(1,2);
		Assert.assertNotNull(dto1);
        Assert.assertEquals(dto1.getRecords().size(),2);
        Assert.assertEquals(dto1.getTotalRecords().longValue(),3L);
		
		
		TareaPageDTO dto2=tareaLogicService.getTareas(2,2);
		Assert.assertNotNull(dto2);
        Assert.assertEquals(dto2.getRecords().size(),1);
        Assert.assertEquals(dto2.getTotalRecords().longValue(),3L);
		
		for(TareaDTO dto:dto1.getRecords()){
        	boolean found=false;
            for(TareaDTO pdto:data){
            	if(dto.getId()==pdto.getId()){
                	found=true;
                }
            }
            Assert.assertTrue(found);
        }
        
        for(TareaDTO dto:dto2.getRecords()){
        	boolean found=false;
            for(TareaDTO pdto:data){
            	if(dto.getId()==pdto.getId()){
                	found=true;
                }
            }
            Assert.assertTrue(found);
        }
        
        TareaPageDTO dto3=tareaLogicService.getTareas(1,3);
		Assert.assertNotNull(dto3);
        Assert.assertEquals(dto3.getRecords().size(),data.size());
        Assert.assertEquals(dto3.getTotalRecords().longValue(),data.size());
		
		for(TareaDTO dto:dto3.getRecords()){
        	boolean found=false;
            for(TareaDTO pdto:data){
            	if(dto.getId()==pdto.getId()){
                	found=true;
                }
            }
            Assert.assertTrue(found);
        }
	}
	
    @Test
	public void getTareasByResponsableTest(){	
        //Creando el foro
        ForoDTO ldto=new ForoDTO();
	ldto.setName(generateRandom(String.class));
	ldto.setTema(generateRandom(String.class));
	ldto.setPresupuesto(generateRandom(Long.class));	
	ForoDTO result=foroLogicService.createForo(ldto);
	Assert.assertNotNull(result);
        
        //Creando la fase
        FaseDTO fdto=new FaseDTO();
        fdto.setName(generateRandom(String.class));
        fdto.setFechaInicio(generateRandomDate());
        fdto.setFechaFin(generateRandomDate());
        FaseDTO resp=faseLogicService.createFase(fdto);
        Assert.assertNotNull(resp);
        Forofase_foroEntity prueba=new Forofase_foroEntity(result.getId(), resp.getId());
        Forofase_foroEntity conex=foroMasterPersistence.createForofase_foroEntity(prueba);
        Assert.assertNotNull(conex);
        
        //Creando Responsable
        ResponsableDTO rdto=new ResponsableDTO();
        rdto.setName(generateRandom(String.class));
        rdto.setCorreo(generateRandom(String.class));
        ResponsableDTO respu=responsableLogicService.createResponsable(rdto);
        Assert.assertNotNull(respu);
        
        //Creando tareas, 2 a un responsable
        List<TareaDTO> listica=new ArrayList<TareaDTO>();
        for(int i=0;i<3;i++)
        {
            TareaDTO tdto=new TareaDTO();
            tdto.setName(generateRandom(String.class));
            tdto.setDescripcion(generateRandom(String.class));
            tdto.setEstado(0);
            tdto.setFechaInicio(generateRandomDate());
            tdto.setFechaFin(generateRandomDate());
            tdto.setResponsable(respu.getName());
            tdto.setResponsable_tareaId(rdto.getId());
            TareaDTO res=tareaLogicService.createTarea(tdto);
            Assert.assertNotNull(res);
            listica.add(res);
            
            Fasetarea_faseEntity pr=new Fasetarea_faseEntity(resp.getId(), res.getId());
            Fasetarea_faseEntity test=faseMasterPersistence.createFasetarea_faseEntity(pr);
            Assert.assertNotNull(test);
        }
        
        //Pruebas
        List<TareaDTO> tareas=(List<TareaDTO>)tareaLogicService.darTareasResponsable(Integer.SIZE, Integer.MIN_VALUE, DEPLOY);
	for(int y=0;y<tareas.size();y++)
        {
            TareaDTO t1=listica.get(y);
            TareaDTO t2=tareas.get(y);
            Assert.assertEquals(t1.getName(), t2.getName());
            Assert.assertEquals(t1.getDescripcion(), t2.getDescripcion());
            Assert.assertTrue(t2.getEstado()==0);
            Assert.assertEquals(t1.getFechaFin(), t2.getFechaFin());
            Assert.assertEquals(t1.getFechaInicio(), t2.getFechaInicio());
            Assert.assertEquals(t1.getResponsable(), t2.getResponsable());
        }
        }
}