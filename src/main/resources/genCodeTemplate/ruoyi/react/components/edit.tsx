import React, { useEffect, useState } from 'react';
import ProForm, {
  ProFormDigit,
  ProFormText,
  ProFormRadio,
  ProFormTextArea,
  DrawerForm,
} from '@ant-design/pro-form';
import { Form, Row, Col, Tree } from 'antd';
import { useIntl, FormattedMessage } from 'umi';
import type { #className#Type } from '../data.d';
import type { DataNode } from 'antd/lib/tree';

export type #className#FormValueType = Record<string, unknown> & Partial<#className#Type>;

export type #className#FormProps = {
  onCancel: (flag?: boolean, formVals?: #className#FormValueType) => void;
  onSubmit: (values: #className#FormValueType) => Promise<void>;
  visible: boolean;
  values: Partial<#className#Type>;
  menuTree: DataNode[];
  menuCheckedKeys: string[];
  statusOptions: any;
};

const #className#Form: React.FC<#className#FormProps> = (props) => {
  const [form] = Form.useForm();

  const { menuTree, menuCheckedKeys } = props;
  const [menuIds, setMenuIds] = useState<any>();
  const { statusOptions } = props;

  useEffect(() => {
    form.resetFields();
    form.setFieldsValue({
      #reactRuoyiSetFieldsValueRows#
      dataScope: props.values.dataScope,
      menuCheckStrictly: props.values.menuCheckStrictly,
      deptCheckStrictly: props.values.deptCheckStrictly,
      status: props.values.status,
      delFlag: props.values.delFlag,
      createBy: props.values.createBy,
      createTime: props.values.createTime,
      updateBy: props.values.updateBy,
      updateTime: props.values.updateTime,
      remark: props.values.remark,
      menuIds: props.values.menuIds,
    });
  }, [form, props]);

  const intl = useIntl();
  const handleClose = () => {
    props.onCancel();
    form.resetFields();
  };
  const handleFinish = async (values: Record<string, any>) => {
    props.onSubmit({ ...values, menuIds } as #className#FormValueType);
  };

  return (
    <DrawerForm
      form={form}
      title={intl.formatMessage({
        id: 'system.#className#.modify_info',
        defaultMessage: '编辑信息',
      })}
      onFinish={handleFinish}
      initialValues={props.values}
      visible={props.visible}
      drawerProps={{
        destroyOnClose: true,
        onClose: handleClose,
        maskClosable: false,
        keyboard: false,
      }}
    >
      #reactRuoyiDrawerFormRows#
    </DrawerForm>
  );
};

export default #className#Form;
