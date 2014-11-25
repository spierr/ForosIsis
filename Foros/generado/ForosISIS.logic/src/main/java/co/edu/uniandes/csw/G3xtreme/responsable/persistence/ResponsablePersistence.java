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

package co.edu.uniandes.csw.G3xtreme.responsable.persistence;

import co.edu.uniandes.csw.G3xtreme.responsable.logic.dto.ResponsableDTO;
import co.edu.uniandes.csw.G3xtreme.responsable.logic.dto.ResponsablePageDTO;
import co.edu.uniandes.csw.G3xtreme.responsable.persistence.api.IResponsablePersistence;
import co.edu.uniandes.csw.G3xtreme.responsable.persistence.converter.ResponsableConverter;
import co.edu.uniandes.csw.G3xtreme.responsable.persistence.entity.ResponsableEntity;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.enterprise.inject.Default;
import javax.persistence.Query;

@Default
@Stateless 
@LocalBean
public class ResponsablePersistence extends _ResponsablePersistence  implements IResponsablePersistence {
    public ResponsableDTO getResponsableId(String name) {
        Query q = entityManager.createQuery("select y from ResponsableEntity y where y.name = '" + name + "'");
        if(q.getResultList().isEmpty())
        {
            return null;
        }
        else
        {
            return ResponsableConverter.entity2PersistenceDTO((ResponsableEntity)q.getResultList().get(0));
        }
    }
    
    @Override
    public ResponsableDTO createResponsable(ResponsableDTO cliente) {
        try
        {
        //Se encripta la contrase�a del DTO
            cliente.setContrasenia(MD5(cliente.getContrasenia()));
        }
        catch (Exception e)
        {
            //No hace nada?
        }
        ResponsableEntity entity = ResponsableConverter.persistenceDTO2Entity(cliente);
        entityManager.persist(entity);
        return ResponsableConverter.entity2PersistenceDTO(entity);
    }
    
    
    
/*M�todo auxiliar que converte bytes a Strings en hexadecimal*/
    private static String convertedToHex(byte[] data) {
        StringBuffer buf = new StringBuffer();
 
        for (int i = 0; i < data.length; i++) {
            int halfOfByte = (data[i] >>> 4) & 0x0F;
            int twoHalfBytes = 0;
 
            do {
                if ((0 <= halfOfByte) && (halfOfByte <= 9))
                {
                    buf.append((char) ('0' + halfOfByte));
                }
                else
                {
                    buf.append((char) ('a' + (halfOfByte - 10)));
                }
 
                halfOfByte = data[i] & 0x0F;
 
            } while (twoHalfBytes++ < 1);
        }
 
        return buf.toString();
    }
/*M�todo que realiza la encripci�n a MD5*/
    public static String MD5(String text)
            throws NoSuchAlgorithmException, UnsupportedEncodingException {
        MessageDigest md;
        md = MessageDigest.getInstance("MD5");
        byte[] md5 = new byte[64];
        md.update(text.getBytes("iso-8859-1"), 0, text.length());
        md5 = md.digest();
        return convertedToHex(md5);
    }

    public ResponsablePageDTO getResponsablesPorNombre(String name) {
        Query count = entityManager.createQuery("select count(r) from ResponsableEntity r");
        Long regCount = 0L;
        regCount = Long.parseLong(count.getSingleResult().toString());
        Query q = entityManager.createQuery("SELECT r FROM ResponsableEntity r WHERE r.name like :name");
        q.setParameter("name", "%"+name+"%");

        ResponsablePageDTO response = new ResponsablePageDTO();
        response.setTotalRecords(regCount);
        response.setRecords(ResponsableConverter.entity2PersistenceDTOList(q.getResultList()));
        return response;
    }
}