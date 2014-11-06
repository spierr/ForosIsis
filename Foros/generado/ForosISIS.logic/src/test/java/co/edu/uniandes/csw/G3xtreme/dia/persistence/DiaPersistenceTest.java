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

package co.edu.uniandes.csw.G3xtreme.dia.persistence;

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


import co.edu.uniandes.csw.G3xtreme.dia.logic.dto.DiaPageDTO;
import co.edu.uniandes.csw.G3xtreme.dia.logic.dto.DiaDTO;
import co.edu.uniandes.csw.G3xtreme.dia.persistence.api.IDiaPersistence;
import co.edu.uniandes.csw.G3xtreme.dia.persistence.entity.DiaEntity;
import co.edu.uniandes.csw.G3xtreme.dia.persistence.converter.DiaConverter;
import static co.edu.uniandes.csw.G3xtreme.util._TestUtil.*;

@RunWith(Arquillian.class)
public class DiaPersistenceTest {

	public static final String DEPLOY = "Prueba";

	@Deployment
	public static WebArchive createDeployment() {
		return ShrinkWrap.create(WebArchive.class, DEPLOY + ".war")
				.addPackage(DiaPersistence.class.getPackage())
				.addPackage(DiaEntity.class.getPackage())
				.addPackage(IDiaPersistence.class.getPackage())
                .addPackage(DiaDTO.class.getPackage())
                .addPackage(DiaConverter.class.getPackage())
				.addAsResource("META-INF/persistence.xml", "META-INF/persistence.xml")
                .addAsWebInfResource("META-INF/beans.xml", "beans.xml");
	}

	@Inject
	private IDiaPersistence diaPersistence;

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
		em.createQuery("delete from DiaEntity").executeUpdate();
	}

	private List<DiaEntity> data=new ArrayList<DiaEntity>();

	private void insertData() {
		for(int i=0;i<3;i++){
			DiaEntity entity=new DiaEntity();
			entity.setName(generateRandom(String.class));
			entity.setFecha(generateRandom(Date.class));
			entity.setCosto(generateRandom(Long.class));
			em.persist(entity);
			data.add(entity);
		}
	}
	
	@Test
	public void createDiaTest(){
		DiaDTO dto=new DiaDTO();
		dto.setName(generateRandom(String.class));
dto.setFecha(generateRandomDate());
		dto.setCosto(generateRandom(Long.class));
		
		DiaDTO result=diaPersistence.createDia(dto);
		
		Assert.assertNotNull(result);
		
		DiaEntity entity=em.find(DiaEntity.class, result.getId());
		
		Assert.assertEquals(dto.getName(), entity.getName());
Assert.assertEquals(parseDate(dto.getFecha()), entity.getFecha());	
		Assert.assertEquals(dto.getCosto(), entity.getCosto());
	}
	
	@Test
	public void getDiasTest(){
		List<DiaDTO> list=diaPersistence.getDias();
		Assert.assertEquals(list.size(), data.size());
        for(DiaDTO dto:list){
        	boolean found=false;
            for(DiaEntity entity:data){
            	if(dto.getId().equals(entity.getId())){
                	found=true;
                }
            }
            Assert.assertTrue(found);
        }
	}
	
	@Test
	public void getDiaTest(){
		DiaEntity entity=data.get(0);
		DiaDTO dto=diaPersistence.getDia(entity.getId());
        Assert.assertNotNull(dto);
		Assert.assertEquals(entity.getName(), dto.getName());
		Assert.assertEquals(entity.getCosto(), dto.getCosto());
        
	}
	
	@Test
	public void deleteDiaTest(){
		DiaEntity entity=data.get(0);
		diaPersistence.deleteDia(entity.getId());
        DiaEntity deleted=em.find(DiaEntity.class, entity.getId());
        Assert.assertNull(deleted);
	}
	
	@Test
	public void updateDiaTest(){
		DiaEntity entity=data.get(0);
		
		DiaDTO dto=new DiaDTO();
		dto.setId(entity.getId());
		dto.setName(generateRandom(String.class));
dto.setFecha(generateRandomDate());
		dto.setCosto(generateRandom(Long.class));
		
		
		diaPersistence.updateDia(dto);
		
		
		DiaEntity resp=em.find(DiaEntity.class, entity.getId());
		
		Assert.assertEquals(dto.getName(), resp.getName());	
Assert.assertEquals(parseDate(dto.getFecha()), resp.getFecha());
		Assert.assertEquals(dto.getCosto(), resp.getCosto());	
	}
	
	@Test
	public void getDiaPaginationTest(){
		//Page 1
		DiaPageDTO dto1=diaPersistence.getDias(1,2);
        Assert.assertNotNull(dto1);
        Assert.assertEquals(dto1.getRecords().size(),2);
        Assert.assertEquals(dto1.getTotalRecords().longValue(),3L);
        //Page 2
        DiaPageDTO dto2=diaPersistence.getDias(2,2);
        Assert.assertNotNull(dto2);
        Assert.assertEquals(dto2.getRecords().size(),1);
        Assert.assertEquals(dto2.getTotalRecords().longValue(),3L);
        
        for(DiaDTO dto:dto1.getRecords()){
        	boolean found=false;	
            for(DiaEntity entity:data){
            	if(dto.getId().equals(entity.getId())){
                	found=true;
                }
            }
            Assert.assertTrue(found);
        }
        
        for(DiaDTO dto:dto2.getRecords()){
        	boolean found=false;
            for(DiaEntity entity:data){
            	if(dto.getId().equals(entity.getId())){
                	found=true;
                }
            }
            Assert.assertTrue(found);
        }
	}
	
}