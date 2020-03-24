package cn.xlf.workflow.dao;

import cn.xlf.workflow.entity.StudentsEntity;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author 类中域:徐林飞
 * @date 2020/3/9 14:27
 */
public interface StudentsDao extends JpaRepository<StudentsEntity,String> {
}
