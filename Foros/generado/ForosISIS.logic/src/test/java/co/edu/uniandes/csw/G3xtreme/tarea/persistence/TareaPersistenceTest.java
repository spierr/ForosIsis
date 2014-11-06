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


import co.edu.uniandes.csw.G3xtreme.tarea.logic.dto.TareaPageDTO;
import co.edu.uniandes.csw.G3xtreme.tarea.logic.dto.TareaDTO;
import co.edu.uniandes.csw.G3xtreme.tarea.persistence.api.ITareaPersistence;
import co.edu.uniandes.csw.G3xtreme.tarea.persistence.entity.TareaEntity;
import co.edu.uniandes.csw.G3xtreme.tarea.persistence.converter.TareaConverter;
import static co.edu.uniandes.csw.G3xtreme.util._TestUtil.*;

@RunWith(Arquillian.class)
public class TareaPersistenceTest {

	public static final String DEPLOY = "Prueba";

	@Deployment
	public static WebArchive createDeployment() {
		return ShrinkWrap.create(WebArchive.class, DEPLOY + ".war")
				.addPackage(TareaPersistence.class.getPackage())
				.addPackage(TareaEntity.class.getPackage())
				.addPackage(ITareaPersistence.class.getPackage())
                .addPackage(TareaDTO.class.getPackage())
                .addPackage(TareaConverter.class.getPackage())
				.addAsResource("META-INF/persistence.xml", "META-INF/persistence.xml")
                .addAsWebInfResource("META-INF/beans.xml", "beans.xml");
	}

	@Inject
	private ITareaPersistence tareaPersistence;

	@PersistenceContext
	private EntityManager em;

	@Inject
	UserTransaction utx;

	@Before
	public void configTest() {
		System.out.println("em: " + em);
		try {
			utx.begin();
			clearData();
			insertData();
			utx.commit();
		} catch (Exception e) {
			e.printStackTrace();
			try {
				utx.rollback();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
	}

	private void clearData() {
		em.createQuery("delete from TareaEntity").executeUpdate();
	}

	private List<TareaEntity> data=new ArrayList<TareaEntity>();

	private void insertData() {
		for(int i=0;i<3;i++){
			TareaEntity entity=new TareaEntity();
			entity.setName(generateRandom(String.class));
			entity.setResponsable(generateRandom(String.class));
			entity.setFechaInicio(generateRandom(Date.class));
			entity.setFechaFin(generateRandom(Date.class));
			entity.setDescripcion(generateRandom(String.class));
			entity.setEstado(generateRandom(Integer.class));
			entity.setResponsable_tareaId(generateRandom(Long.class));
			em.persist(entity);
			data.add(entity);
		}
	}
	
	@Test
	public void createTareaTest(){
		TareaDTO dto=new TareaDTO();
		dto.setName(generateRandom(String.class));
		dto.setResponsable(generateRandom(String.class));
dto.setFechaInicio(generateRandomDate());
dto.setFechaFin(generateRandomDate());
		dto.setDescripcion(generateRandom(String.class));
		dto.setEstado(generateRandom(Integer.class));
		dto.setResponsable_tareaId(generateRandom(Long.class));
		
		TareaDTO result=tareaPersistence.createTarea(dto);
		
		Assert.assertNotNull(result);
		
		TareaEntity entity=em.find(TareaEntity.class, result.getId());
		
		Assert.assertEquals(dto.getName(), entity.getName());
		Assert.assertEquals(dto.getResponsable(), entity.getResponsable());
Assert.assertEquals(parseDate(dto.getFechaInicio()), entity.getFechaInicio());	
Assert.assertEquals(parseDate(dto.getFechaFin()), entity.getFechaFin());	
		Assert.assertEquals(dto.getDescripcion(), entity.getDescripcion());
		Assert.assertEquals(dto.getEstado(), entity.getEstado());
		Assert.assertEquals(dto.getResponsable_tareaId(), entity.getResponsable_tareaId());
	}
	
	@Test
	public void getTareasTest(){
		List<TareaDTO> list=tareaPersistence.getTareas();
		Assert.assertEquals(list.size(), data.size());
        for(TareaDTO dto:list){
        	boolean found=false;
            for(TareaEntity entity:data){
            	if(dto.getId().equals(entity.getId())){
                	found=true;
                }
            }
            Assert.assertTrue(found);
        }
	}
	
	@Test
	public void getTareaTest(){
		TareaEntity entity=data.get(0);
		TareaDTO dto=tareaPersistence.getTarea(entity.getId());
        Assert.assertNotNull(dto);
		Assert.assertEquals(entity.getName(), dto.getName());
		Assert.assertEquals(entity.getResponsable(), dto.getResponsable());
		Assert.assertEquals(entity.getDescripcion(), dto.getDescripcion());
		Assert.assertEquals(entity.getEstado(), dto.getEstado());
		Assert.assertEquals(entity.getResponsable_tareaId(), dto.getResponsable_tareaId());
        
	}
	
	@Test
	public void deleteTareaTest(){
		TareaEntity entity=data.get(0);
		tareaPersistence.deleteTarea(entity.getId());
        TareaEntity deleted=em.find(TareaEntity.class, entity.getId());
        Assert.assertNull(deleted);
	}
	
	@Test
	public void updateTareaTest(){
		TareaEntity entity=data.get(0);
		
		TareaDTO dto=new TareaDTO();
		dto.setId(entity.getId());
		dto.setName(generateRandom(String.class));
		dto.setResponsable(generateRandom(String.class));
dto.setFechaInicio(generateRandomDate());
dto.setFechaFin(generateRandomDate());
		dto.setDescripcion(generateRandom(String.class));
		dto.setEstado(generateRandom(Integer.class));
		dto.setResponsable_tareaId(generateRandom(Long.class));
		
		
		tareaPersistence.updateTarea(dto);
		
		
		TareaEntity resp=em.find(TareaEntity.class, entity.getId());
		
		Assert.assertEquals(dto.getName(), resp.getName());	
		Assert.assertEquals(dto.getResponsable(), resp.getResponsable());	
Assert.assertEquals(parseDate(dto.getFechaInicio()), resp.getFechaInicio());
Assert.assertEquals(parseDate(dto.getFechaFin()), resp.getFechaFin());
		Assert.assertEquals(dto.getDescripcion(), resp.getDescripcion());	
		Assert.assertEquals(dto.getEstado(), resp.getEstado());	
		Assert.assertEquals(dto.getResponsable_tareaId(), resp.getResponsable_tareaId());	
	}
	
	@Test
	public void getTareaPaginationTest(){
		//Page 1
		TareaPageDTO dto1=tareaPersistence.getTareas(1,2);
        Assert.assertNotNull(dto1);
        Assert.assertEquals(dto1.getRecords().size(),2);
        Assert.assertEquals(dto1.getTotalRecords().longValue(),3L);
        //Page 2
        TareaPageDTO dto2=tareaPersistence.getTareas(2,2);
        Assert.assertNotNull(dto2);
        Assert.assertEquals(dto2.getRecords().size(),1);
        Assert.assertEquals(dto2.getTotalRecords().longValue(),3L);
        
        for(TareaDTO dto:dto1.getRecords()){
        	boolean found=false;	
            for(TareaEntity entity:data){
            	if(dto.getId().equals(entity.getId())){
                	found=true;
                }
            }
            Assert.assertTrue(found);
        }
        
        for(TareaDTO dto:dto2.getRecords()){
        	boolean found=false;
            for(TareaEntity entity:data){
            	if(dto.getId().equals(entity.getId())){
                	found=true;
                }
            }
            Assert.assertTrue(found);
        }
	}
	
}