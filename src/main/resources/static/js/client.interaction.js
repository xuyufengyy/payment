/**
 * Created by cln on 2015/8/3.
 */

/**
 * �ͻ��ˣ�android��ios�����õĹ���js����
 * @param cmd ������
 * @param para ����json���ַ���
 */
function commonMethod(cmd, para) {
    if(cmd=="operatingSystem"){
        operatingSystem(para);
    }
}
/**
 *  ���ò���ϵͳ�����ҷ���title
 * @param operatingSystem
 */
function operatingSystem(operatingSystem){
    var vOperatingSystem=eval("(" +operatingSystem+ ")");
    v_operatingSystem=vOperatingSystem;
}

var v_operatingSystem;
