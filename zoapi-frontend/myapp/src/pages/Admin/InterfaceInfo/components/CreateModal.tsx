import {
  ProColumns,
  ProTable,
} from '@ant-design/pro-components';
import '@umijs/max';
import { Modal } from 'antd';
import React from 'react';

// prop 组件传递 ， 还能传递方法变量涨知识
export type Props = {
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
  const {visible , columns , onCancel , onSubmit} = props;
  return (
    <Modal
      visible={visible}
      footer={null}
      onCancel={()=>onCancel?.()}>
      <ProTable
        type = "form"
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
