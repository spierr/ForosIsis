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

package co.edu.uniandes.csw.G3xtreme.coordinador.logic.ejb;

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


import co.edu.uniandes.csw.G3xtreme.coordinador.logic.dto.CoordinadorPageDTO;
import co.edu.uniandes.csw.G3xtreme.coordinador.logic.dto.CoordinadorDTO;
import co.edu.uniandes.csw.G3xtreme.coordinador.logic.api.ICoordinadorLogicService;
import co.edu.uniandes.csw.G3xtreme.coordinador.persistence.CoordinadorPersistence;
import co.edu.uniandes.csw.G3xtreme.coordinador.persistence.api.ICoordinadorPersistence;
import co.edu.uniandes.csw.G3xtreme.coordinador.persistence.entity.CoordinadorEntity;
import co.edu.uniandes.csw.G3xtreme.coordinador.persistence.converter.CoordinadorConverter;
import static co.edu.uniandes.csw.G3xtreme.util._TestUtil.*;

@RunWith(Arquillian.class)
public class CoordinadorLogicServiceTest {

	public static final String DEPLOY = "Prueba";

	@Deployment
	public static WebArchive createDeployment() {
		return ShrinkWrap.create(WebArchive.class, DEPLOY + ".war")
				.addPackage(CoordinadorLogicService.class.getPackage())
				.addPackage(ICoordinadorLogicService.class.getPackage())
				.addPackage(CoordinadorPersistence.class.getPackage())
				.addPackage(CoordinadorEntity.class.getPackage())
				.addPackage(ICoordinadorPersistence.class.getPackage())
                .addPackage(CoordinadorDTO.class.getPackage())
                .addPackage(CoordinadorConverter.class.getPackage())
                .addPackage(CoordinadorEntity.class.getPackage())
				.addAsResource("META-INF/persistence.xml", "META-INF/persistence.xml")
                .addAsWebInfResource("META-INF/beans.xml", "beans.xml");
	}

	@Inject
	private ICoordinadorLogicService coordinadorLogicService;
	
	@Inject
	private ICoordinadorPersistence coordinadorPersistence;	

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
		List<CoordinadorDTO> dtos=coordinadorPersistence.getCoordinadors();
		for(CoordinadorDTO dto:dtos){
			coordinadorPersistence.deleteCoordinador(dto.getId());
		}
	}

	private List<CoordinadorDTO> data=new ArrayList<CoordinadorDTO>();

	private void insertData() {
		for(int i=0;i<3;i++){
			CoordinadorDTO pdto=new CoordinadorDTO();
			pdto.setName(generateRandom(String.class));
			pdto.setCorreo(generateRandom(String.class));
			pdto=coordinadorPersistence.createCoordinador(pdto);
			data.add(pdto);
		}
	}
	
	@Test
	public void createCoordinadorTest(){
		CoordinadorDTO ldto=new CoordinadorDTO();
		ldto.setName(generateRandom(String.class));
		ldto.setCorreo(generateRandom(String.class));
		
		
		CoordinadorDTO result=coordinadorLogicService.createCoordinador(ldto);
		
		Assert.assertNotNull(result);
		
		CoordinadorDTO pdto=coordinadorPersistence.getCoordinador(result.getId());
		
		Assert.assertEquals(ldto.getName(), pdto.getName());	
		Assert.assertEquals(ldto.getCorreo(), pdto.getCorreo());	
	}
	
	@Test
	public void getCoordinadorsTest(){
		List<CoordinadorDTO> list=coordinadorLogicService.getCoordinadors();
		Assert.assertEquals(list.size(), data.size());
        for(CoordinadorDTO ldto:list){
        	boolean found=false;
            for(CoordinadorDTO pdto:data){
            	if(ldto.getId()==pdto.getId()){
                	found=true;
                }
            }
            Assert.assertTrue(found);
        }
	}
	
	@Test
	public void getCoordinadorTest(){
		CoordinadorDTO pdto=data.get(0);
		CoordinadorDTO ldto=coordinadorLogicService.getCoordinador(pdto.getId());
        Assert.assertNotNull(ldto);
		Assert.assertEquals(pdto.getName(), ldto.getName());
		Assert.assertEquals(pdto.getCorreo(), ldto.getCorreo());
        
	}
	
	@Test
	public void deleteCoordinadorTest(){
		CoordinadorDTO pdto=data.get(0);
		coordinadorLogicService.deleteCoordinador(pdto.getId());
        CoordinadorDTO deleted=coordinadorPersistence.getCoordinador(pdto.getId());
        Assert.assertNull(deleted);
	}
	
	@Test
	public void updateCoordinadorTest(){
		CoordinadorDTO pdto=data.get(0);
		
		CoordinadorDTO ldto=new CoordinadorDTO();
		ldto.setId(pdto.getId());
		ldto.setName(generateRandom(String.class));
		ldto.setCorreo(generateRandom(String.class));
		
		
		coordinadorLogicService.updateCoordinador(ldto);
		
		
		CoordinadorDTO resp=coordinadorPersistence.getCoordinador(pdto.getId());
		
		Assert.assertEquals(ldto.getName(), resp.getName());	
		Assert.assertEquals(ldto.getCorreo(), resp.getCorreo());	
	}
	
	@Test
	public void getCoordinadorPaginationTest(){
		
		CoordinadorPageDTO dto1=coordinadorLogicService.getCoordinadors(1,2);
		Assert.assertNotNull(dto1);
        Assert.assertEquals(dto1.getRecords().size(),2);
        Assert.assertEquals(dto1.getTotalRecords().longValue(),3L);
		
		
		CoordinadorPageDTO dto2=coordinadorLogicService.getCoordinadors(2,2);
		Assert.assertNotNull(dto2);
        Assert.assertEquals(dto2.getRecords().size(),1);
        Assert.assertEquals(dto2.getTotalRecords().longValue(),3L);
		
		for(CoordinadorDTO dto:dto1.getRecords()){
        	boolean found=false;
            for(CoordinadorDTO pdto:data){
            	if(dto.getId()==pdto.getId()){
                	found=true;
                }
            }
            Assert.assertTrue(found);
        }
        
        for(CoordinadorDTO dto:dto2.getRecords()){
        	boolean found=false;
            for(CoordinadorDTO pdto:data){
            	if(dto.getId()==pdto.getId()){
                	found=true;
                }
            }
            Assert.assertTrue(found);
        }
        
        CoordinadorPageDTO dto3=coordinadorLogicService.getCoordinadors(1,3);
		Assert.assertNotNull(dto3);
        Assert.assertEquals(dto3.getRecords().size(),data.size());
        Assert.assertEquals(dto3.getTotalRecords().longValue(),data.size());
		
		for(CoordinadorDTO dto:dto3.getRecords()){
        	boolean found=false;
            for(CoordinadorDTO pdto:data){
            	if(dto.getId()==pdto.getId()){
                	found=true;
                }
            }
            Assert.assertTrue(found);
        }
	}
	
	
}