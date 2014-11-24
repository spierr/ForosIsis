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

package co.edu.uniandes.csw.G3xtreme.dia_inscripcion.logic.ejb;

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
import java.util.*;


import co.edu.uniandes.csw.G3xtreme.dia_inscripcion.logic.dto.Dia_InscripcionPageDTO;
import co.edu.uniandes.csw.G3xtreme.dia_inscripcion.logic.dto.Dia_InscripcionDTO;
import co.edu.uniandes.csw.G3xtreme.dia_inscripcion.logic.api.IDia_InscripcionLogicService;
import co.edu.uniandes.csw.G3xtreme.dia_inscripcion.persistence.Dia_InscripcionPersistence;
import co.edu.uniandes.csw.G3xtreme.dia_inscripcion.persistence.api.IDia_InscripcionPersistence;
import co.edu.uniandes.csw.G3xtreme.dia_inscripcion.persistence.entity.Dia_InscripcionEntity;
import co.edu.uniandes.csw.G3xtreme.dia_inscripcion.persistence.converter.Dia_InscripcionConverter;
import static co.edu.uniandes.csw.G3xtreme.util._TestUtil.*;

@RunWith(Arquillian.class)
public class Dia_InscripcionLogicServiceTest {

	public static final String DEPLOY = "Prueba";

	@Deployment
	public static WebArchive createDeployment() {
		return ShrinkWrap.create(WebArchive.class, DEPLOY + ".war")
				.addPackage(Dia_InscripcionLogicService.class.getPackage())
				.addPackage(IDia_InscripcionLogicService.class.getPackage())
				.addPackage(Dia_InscripcionPersistence.class.getPackage())
				.addPackage(Dia_InscripcionEntity.class.getPackage())
				.addPackage(IDia_InscripcionPersistence.class.getPackage())
                .addPackage(Dia_InscripcionDTO.class.getPackage())
                .addPackage(Dia_InscripcionConverter.class.getPackage())
                .addPackage(Dia_InscripcionEntity.class.getPackage())
				.addAsResource("META-INF/persistence.xml", "META-INF/persistence.xml")
                .addAsWebInfResource("META-INF/beans.xml", "beans.xml");
	}

	@Inject
	private IDia_InscripcionLogicService dia_InscripcionLogicService;
	
	@Inject
	private IDia_InscripcionPersistence dia_InscripcionPersistence;	

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
		List<Dia_InscripcionDTO> dtos=dia_InscripcionPersistence.getDia_Inscripcions();
		for(Dia_InscripcionDTO dto:dtos){
			dia_InscripcionPersistence.deleteDia_Inscripcion(dto.getId());
		}
	}

	private List<Dia_InscripcionDTO> data=new ArrayList<Dia_InscripcionDTO>();

	private void insertData() {
		for(int i=0;i<3;i++){
			Dia_InscripcionDTO pdto=new Dia_InscripcionDTO();
			pdto.setName(generateRandom(String.class));
			pdto.setFecha(generateRandomDate());
			pdto.setNumInscritos(generateRandom(Integer.class));
			pdto=dia_InscripcionPersistence.createDia_Inscripcion(pdto);
			data.add(pdto);
		}
	}
	
	@Test
	public void createDia_InscripcionTest(){
		Dia_InscripcionDTO ldto=new Dia_InscripcionDTO();
		ldto.setName(generateRandom(String.class));
		ldto.setFecha(generateRandomDate());
		ldto.setNumInscritos(generateRandom(Integer.class));
		
		
		Dia_InscripcionDTO result=dia_InscripcionLogicService.createDia_Inscripcion(ldto);
		
		Assert.assertNotNull(result);
		
		Dia_InscripcionDTO pdto=dia_InscripcionPersistence.getDia_Inscripcion(result.getId());
		
		Assert.assertEquals(ldto.getName(), pdto.getName());	
		Assert.assertEquals(ldto.getFecha(), pdto.getFecha());	
		Assert.assertEquals(ldto.getNumInscritos(), pdto.getNumInscritos());	
	}
	
	@Test
	public void getDia_InscripcionsTest(){
		List<Dia_InscripcionDTO> list=dia_InscripcionLogicService.getDia_Inscripcions();
		Assert.assertEquals(list.size(), data.size());
        for(Dia_InscripcionDTO ldto:list){
        	boolean found=false;
            for(Dia_InscripcionDTO pdto:data){
            	if(ldto.getId()==pdto.getId()){
                	found=true;
                }
            }
            Assert.assertTrue(found);
        }
	}
	
	@Test
	public void getDia_InscripcionTest(){
		Dia_InscripcionDTO pdto=data.get(0);
		Dia_InscripcionDTO ldto=dia_InscripcionLogicService.getDia_Inscripcion(pdto.getId());
        Assert.assertNotNull(ldto);
		Assert.assertEquals(pdto.getName(), ldto.getName());
		Assert.assertEquals(pdto.getFecha(), ldto.getFecha());
		Assert.assertEquals(pdto.getNumInscritos(), ldto.getNumInscritos());
        
	}
	
	@Test
	public void deleteDia_InscripcionTest(){
		Dia_InscripcionDTO pdto=data.get(0);
		dia_InscripcionLogicService.deleteDia_Inscripcion(pdto.getId());
        Dia_InscripcionDTO deleted=dia_InscripcionPersistence.getDia_Inscripcion(pdto.getId());
        Assert.assertNull(deleted);
	}
	
	@Test
	public void updateDia_InscripcionTest(){
		Dia_InscripcionDTO pdto=data.get(0);
		
		Dia_InscripcionDTO ldto=new Dia_InscripcionDTO();
		ldto.setId(pdto.getId());
		ldto.setName(generateRandom(String.class));
		ldto.setFecha(generateRandomDate());
		ldto.setNumInscritos(generateRandom(Integer.class));
		
		
		dia_InscripcionLogicService.updateDia_Inscripcion(ldto);
		
		
		Dia_InscripcionDTO resp=dia_InscripcionPersistence.getDia_Inscripcion(pdto.getId());
		
		Assert.assertEquals(ldto.getName(), resp.getName());	
		Assert.assertEquals(ldto.getFecha(), resp.getFecha());	
		Assert.assertEquals(ldto.getNumInscritos(), resp.getNumInscritos());	
	}
	
	@Test
	public void getDia_InscripcionPaginationTest(){
		
		Dia_InscripcionPageDTO dto1=dia_InscripcionLogicService.getDia_Inscripcions(1,2);
		Assert.assertNotNull(dto1);
        Assert.assertEquals(dto1.getRecords().size(),2);
        Assert.assertEquals(dto1.getTotalRecords().longValue(),3L);
		
		
		Dia_InscripcionPageDTO dto2=dia_InscripcionLogicService.getDia_Inscripcions(2,2);
		Assert.assertNotNull(dto2);
        Assert.assertEquals(dto2.getRecords().size(),1);
        Assert.assertEquals(dto2.getTotalRecords().longValue(),3L);
		
		for(Dia_InscripcionDTO dto:dto1.getRecords()){
        	boolean found=false;
            for(Dia_InscripcionDTO pdto:data){
            	if(dto.getId()==pdto.getId()){
                	found=true;
                }
            }
            Assert.assertTrue(found);
        }
        
        for(Dia_InscripcionDTO dto:dto2.getRecords()){
        	boolean found=false;
            for(Dia_InscripcionDTO pdto:data){
            	if(dto.getId()==pdto.getId()){
                	found=true;
                }
            }
            Assert.assertTrue(found);
        }
        
        Dia_InscripcionPageDTO dto3=dia_InscripcionLogicService.getDia_Inscripcions(1,3);
		Assert.assertNotNull(dto3);
        Assert.assertEquals(dto3.getRecords().size(),data.size());
        Assert.assertEquals(dto3.getTotalRecords().longValue(),data.size());
		
		for(Dia_InscripcionDTO dto:dto3.getRecords()){
        	boolean found=false;
            for(Dia_InscripcionDTO pdto:data){
            	if(dto.getId()==pdto.getId()){
                	found=true;
                }
            }
            Assert.assertTrue(found);
        }
	}
	
	
}