import { addRule, removeRule, rule, updateRule } from '@/services/ant-design-pro/api';
import { PlusOutlined } from '@ant-design/icons';
import type { ActionType, ProColumns, ProDescriptionsItemProps } from '@ant-design/pro-components';
import {
  FooterToolbar,
  PageContainer,
  ProDescriptions,
  ProTable,
} from '@ant-design/pro-components';
import '@umijs/max';
import { Button, Drawer, Input, message } from 'antd';
import React, { useRef, useState } from 'react';
import {
  addInterfaceInfoUsingPost,
  listInterfaceInfoByPageUsingGet,
  offlineInterfaceInfoUsingPost,
  onlineInterfaceInfoUsingPost,
  updateInterfaceInfoUsingPost
} from "@/services/zoapi-backend/interfaceInfoController";
import type {SortOrder} from "antd/lib/table/interface";
import CreateModal from "@/pages/Admin/InterfaceInfo/components/CreateModal";
import UpdateModal from "@/pages/Admin/InterfaceInfo/components/UpdateModal";

const TableList: React.FC = () => {
  /**
   * @en-US Pop-up window of new window
   * @zh-CN 新建窗口的弹窗
   *  */
  const [createModalOpen, handleModalOpen] = useState<boolean>(false);
  /**
   * @en-US The pop-up window of the distribution update window
   * @zh-CN 分布更新窗口的弹窗
   * */
  const [updateModalOpen, handleUpdateModalOpen] = useState<boolean>(false);
  const [showDetail, setShowDetail] = useState<boolean>(false);
  const actionRef = useRef<ActionType>();
  const [currentRow, setCurrentRow] = useState<API.InterfaceInfo>();
  const [selectedRowsState, setSelectedRows] = useState<API.InterfaceInfo[]>([]);

  /**
   * @en-US Add node
   * @zh-CN 添加接口信息行为
   * @param fields
   */
  const handleAdd = async (fields: API.InterfaceInfo) => {
    const hide = message.loading('正在添加');
    try {
      await addInterfaceInfoUsingPost({
        ...fields,
      });
      hide();
      // 创建成功
      message.success('Added successfully');
      // 关闭模态框
      handleModalOpen(false);
      return true;
    } catch (error) {
      hide();
      message.error('Adding failed, please try again!');
      return false;
    }
  };

  /**
   * @en-US Update node
   * @zh-CN 更新节点
   *
   * @param fields
   */
  const handleUpdate = async (fields: API.InterfaceInfo) => {
    const hide = message.loading('修改中');
    try {
      await updateInterfaceInfoUsingPost({
        // 要另取 id ， 因为 value 取不到 index type
        id:currentRow.id,
        ...fields,
      });
      hide();
      // 修改成功
      message.success('修改成功');
      return true;
    } catch (error) {
      hide();
      // 操作失败
      message.error('Configuration failed, please try again!');
      return false;
    }
  };

  /**
   *  Delete node
   * @zh-CN 删除节点
   *
   * @param selectedRows
   */
  const handleRemove = async (record: API.InterfaceInfo) => {
    // hide 设置的加载信息 ： 正在删除
    const hide = message.loading('正在删除');
    if (!record) return true;
    try {
      await removeRule({
        key: selectedRows.map((row) => row.key),
      });
      hide();
      message.success('Deleted successfully and will refresh soon');
      return true;
    } catch (error) {
      hide();
      message.error('Delete failed, please try again');
      return false;
    }
  };

  /**
   *  Delete node
   * @zh-CN 发布节点
   *
   * @param record
   */
  const handleOnline = async (record: API.IdRequest) => {
    // hide 设置的加载信息 ： 正在删除
    const hide = message.loading('发布中');
    // 如果接口数据为空
    if (!record) return true;
    try {
      // 调用发布接口的post请求
      await onlineInterfaceInfoUsingPost({
        id : record.id
      });
      hide();
      message.success('上线成功');
      // 重新加载数据
      actionRef.current?.reload();
      return true;
    } catch (error) {
      hide();
      message.error('上线失败');
      return false;
    }
  };

  /**
   *  Delete node
   * @zh-CN 下线节点
   *
   * @param record
   */
  const handleOffline = async (record: API.IdRequest) => {
    // hide 设置的加载信息 ： 正在删除
    const hide = message.loading('下线中');
    // 如果接口数据为空
    if (!record) return true;
    try {
      // 调用发布接口的post请求
      await offlineInterfaceInfoUsingPost({
        id : record.id
      });
      hide();
      message.success('下线成功');
      // 重新加载数据
      actionRef.current?.reload();
      return true;
    } catch (error) {
      hide();
      message.error('下线失败');
      return false;
    }
  };

  // 定义全局 columns 接口信息描述 , 方便给组件传递
  const columns: ProColumns<API.InterfaceInfo>[] = [
    {
      title: 'id',
      dataIndex: 'id',
      valueType: 'index',
    },
    {
      title: '接口名称',
      dataIndex: 'name',
      valueType: 'text',
      // anti 的表单规则
      formItemProps:{
        rules:[
          {
            required:true,
            message:"请填写接口名称"
          }
        ]
      }
    },
    {
      title: '描述',
      dataIndex: 'description',
      valueType:'textarea'
    },
    {
      title: '请求方法',
      dataIndex: 'method',
      valueType: 'text',
      formItemProps:{
        rules:[
          {
            required:true,
            message:"请填写请求方法"
          }
        ]
      }
    },
    {
      title: 'URL',
      dataIndex: 'url',
      valueType:'textarea',
      formItemProps:{
        rules:[
          {
            required:true,
            message:"请填写URL"
          }
        ]
      }
    },
    {
      title: '请求参数',
      dataIndex: 'requestParams',
      valueType:'jsonCode',
    },
    {
      title: '请求头',
      dataIndex: 'requestHeader',
      valueType:'jsonCode',
      formItemProps:{
        rules:[
          {
            required:true,
            message:"请填写请求头"
          }
        ]
      }
    },
    {
      title: '响应头',
      dataIndex: 'responseHeader',
      valueType:'jsonCode'
    },
    {
      title: '状态',
      dataIndex: 'status',
      hideInForm: true,
      valueEnum: {
        0: {
          text: '关闭',
          status: 'Default',
        },
        1: {
          text: '开启',
          status: 'Processing',
        },
      },
    },
    {
      title: '创建时间',
      dataIndex: 'createTime',
      valueType: 'dateTime',
      // anti 特殊属性 ， 在特殊组件form下隐藏
      hideInForm: true,
    },
    {
      title: '更新时间',
      dataIndex: 'updateTime',
      valueType: 'dateTime',
      hideInForm: true,
    },
    {
      title: '操作',
      dataIndex: 'option',
      valueType: 'option',
      render: (_, record) => [
        <a
          key="config"
          onClick={() => {
            handleUpdateModalOpen(true);
            // 设置全局单行数据
            setCurrentRow(record);
          }}
        >
          修改
        </a>,
        <a
          key="config"
          onClick={() => {
            handleRemove(record);
          }}
        >
          删除
        </a>,
        record.status === 0 ?
        <Button
          key = "online"
          type="text"
          danger
          onClick={() => {
            handleOnline(record);
          }}
        >
          发布
        </Button> :null,
        record.status === 1 ?
        <Button
          key = "offline"
          type="text"
          danger
          onClick={() => {
            handleOffline(record);
          }}
        >
          下线
        </Button> : null,
      ],
    },
  ];
  return (
    <PageContainer>
      <ProTable<API.RuleListItem, API.PageParams>
        headerTitle={'查询表格'}
        actionRef={actionRef}
        rowKey="key"
        search={{
          labelWidth: 120,
        }}
        toolBarRender={() => [
          <Button
            type="primary"
            key="primary"
            onClick={() => {
              handleModalOpen(true);
            }}
          >
            <PlusOutlined /> 新建
          </Button>,
        ]}
        // 创造一个方法函数对应上request才能给它赋值，（请求参数，返回值）
        request={async (params, sort: Record<string, SortOrder>, filter: Record<string, (string | number)[] | null>)=>{
          const res = await listInterfaceInfoByPageUsingGet({
            ...params
          })
          console.log(res);
          // 如果后端请求有data
          if(res?.data) {
            return {
              data: res?.data.records || [],
              success: true,
              total: res?.data.total || 0,
            }
          }else{
            return {
              // 没data 的默认值
              data:[],
              success: false,
              total:0,
            }
        }
        }}

        columns={columns}
        rowSelection={{
          onChange: (_, selectedRows) => {
            setSelectedRows(selectedRows);
          },
        }}
      />
      {selectedRowsState?.length > 0 && (
        <FooterToolbar
          extra={
            <div>
              已选择{' '}
              <a
                style={{
                  fontWeight: 600,
                }}
              >
                {selectedRowsState.length}
              </a>{' '}
              项 &nbsp;&nbsp;
              <span>
                服务调用次数总计 {selectedRowsState.reduce((pre, item) => pre + item.callNo!, 0)} 万
              </span>
            </div>
          }
        >
          <Button
            onClick={async () => {
              await handleRemove(selectedRowsState);
              setSelectedRows([]);
              actionRef.current?.reloadAndRest?.();
            }}
          >
            批量删除
          </Button>
          <Button type="primary">批量审批</Button>
        </FooterToolbar>
      )}

      <UpdateModal
        // 组件可以看成一个实例 ， column and  value 相当于内部维护了一个map
        // 编辑列信息
        columns={columns}
        onSubmit={async (value) => {
          const success = await handleUpdate(value);
          if (success) {
            handleUpdateModalOpen(false);
            setCurrentRow(undefined);
            if (actionRef.current) {
              actionRef.current.reload();
            }
          }
        }}
        onCancel={() => {
          handleUpdateModalOpen(false);
          if (!showDetail) {
            setCurrentRow(undefined);
          }
        }}
        visible={updateModalOpen}
        values={currentRow || {}}
      />

      <Drawer
        width={600}
        open={showDetail}
        onClose={() => {
          setCurrentRow(undefined);
          setShowDetail(false);
        }}
        closable={false}
      >
        {currentRow?.name && (
          <ProDescriptions<API.RuleListItem>
            column={2}
            title={currentRow?.name}
            request={async () => ({
              data: currentRow || {},
            })}
            params={{
              id: currentRow?.name,
            }}
            columns={columns as ProDescriptionsItemProps<API.RuleListItem>[]}
          />
        )}
      </Drawer>

      {/* 创建一个 自定义的createModal组件 ， 点击新增的时候唤出 */}
      <CreateModal
        columns={columns}
        // 取消函数
        onCancel={
          ()=>{
            handleModalOpen(false);
          }
        }
        // 提交函数 ， 后端submit
        onSubmit={(values)=>{
          handleAdd(values);
        }}
        // 可视化 boolean
        visible={createModalOpen}
      />

    </PageContainer>
  );
};
export default TableList;
