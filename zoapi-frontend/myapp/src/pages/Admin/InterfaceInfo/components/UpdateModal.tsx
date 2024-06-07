import {
  ProColumns,
  ProTable,
} from '@ant-design/pro-components';
import '@umijs/max';
import { Modal } from 'antd';
import React, {useEffect, useRef} from 'react';
import {ProFormInstance} from "@ant-design/pro-form/lib";

// prop 组件传递 ， 还能传递方法变量涨知识
export type Props = {
  // 表单中需要编辑的数据
  values : API.InterfaceInfo;
  // 表格的列定义
  columns : ProColumns<API.InterfaceInfo>[];
  // 取消按钮
  onCancel: () => void;
  // 用户提交表单，将输入传给后台
  onSubmit: (value:API.InterfaceInfo) => Promise<void>;
  // 模态框是否可见
  visible : boolean;
};

const CreateModal: React.FC<Props> = (props) => {
  // 解构赋值
  const {values,visible , columns , onCancel , onSubmit} = props;
  const formRef = useRef<ProFormInstance>();
  // todo:没看懂
  // 防止修改表单内容一直是同一个内容 ， 监听value 变化
  // 使用React的useEffect在值改变时候更新表单的值
  useEffect(()=>{
    if(formRef){
      formRef.current?.setFieldsValue(values);
    }
  },[values])
  return (
    <Modal
      visible={visible}
      footer={null}
      onCancel={()=>onCancel?.()}>
      <ProTable
        type = "form"
        formRef= {formRef}
        columns = {columns}
        onSubmit = {async (value) => {
          onSubmit?.(value);
        }
        }
      />
    </Modal>
  );
};
export default CreateModal;
