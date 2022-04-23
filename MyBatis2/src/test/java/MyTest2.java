import com.dao.StudentDao;
import com.dao.impl.StudentDaoImpl;
import com.domain.Student;
import org.junit.Test;

import javax.swing.plaf.synth.SynthFormattedTextFieldUI;

public class MyTest2 {

    @Test
    public void testSelectOne(){
        StudentDao dao = new StudentDaoImpl();
        Student student = dao.selectStudentById(2);
        System.out.println("通过dao执行方法，得到的对象： "+student);

    }
}
