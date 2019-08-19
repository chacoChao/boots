package com.chaco.chao.agent;

import com.sun.tools.attach.AgentInitializationException;
import com.sun.tools.attach.AgentLoadException;
import com.sun.tools.attach.AttachNotSupportedException;
import com.sun.tools.attach.VirtualMachine;
import javassist.CannotCompileException;
import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtMethod;
import javassist.expr.ExprEditor;
import javassist.expr.MethodCall;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.IllegalClassFormatException;
import java.lang.instrument.Instrumentation;
import java.security.ProtectionDomain;

/**
 * author:zhaopeiyan001
 * Date:2019-08-19 16:47
 */
public class InstrumentationExample {

    public void virtualMachine() throws Exception {
        VirtualMachine attach = null;
        try {
            attach = VirtualMachine.attach("1234");

            attach.loadAgent(".../agent.jar");
        } finally {
            attach.detach();
        }

    }

    // Java agent指定的premain方法，会在main方法之前被调用
    public static void premain(String args, Instrumentation inst) {
        // Instrumentation提供的addTransformer方法，在类加载时会回调ClassFileTransformer接口
        inst.addTransformer(new ClassFileTransformer() {
            @Override
            public byte[] transform(ClassLoader loader, String className, Class<?> classBeingRedefined,
                                    ProtectionDomain protectionDomain, byte[] classfileBuffer)
                    throws IllegalClassFormatException {

                if (!"com/test/TestClass".equals(className)) {
                    //只修改指定的class
                    return classfileBuffer;
                }

                // 开发者在此自定义做字节码操作，将传入的字节码修改后返回
                // 通常这里需要字节码操作框架
                // ......
                byte[] transformed = null;
                CtClass cl = null;
                try {
                    ClassPool pool = ClassPool.getDefault();
                    cl = pool.makeClass(new ByteArrayInputStream(classfileBuffer));
                    CtMethod[] methods = cl.getDeclaredMethods();
                    for (int i = 0; i < methods.length; i++) {

                        methods[i].instrument(new ExprEditor() {
                            @Override
                            public void edit(MethodCall m) throws CannotCompileException {
//                                super.edit(m);
                                // 把方法体直接替换掉，其中 $proceed($$);是javassist的语法，用来表示原方法体的调用
                                m.replace("{ long stime = System.currentTimeMillis();" + " $_ = $proceed($$);"
                                        + "System.out.println(\"" + m.getClassName() + "." + m.getMethodName()
                                        + " cost:\" + (System.currentTimeMillis() - stime) + \" ms\"); }");
                            }
                        });

                    }
                    // javassist会把输入的Java代码再编译成字节码byte[]
                    transformed = cl.toBytecode();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (CannotCompileException e) {
                    e.printStackTrace();
                } finally {
                    if (null != cl) {
                        // ClassPool默认不会回收，需要手动清理
                        cl.detach();
                    }
                }
                return transformed;
            }
        });
    }
}
