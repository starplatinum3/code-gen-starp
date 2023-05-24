import React, { useState } from 'react';
// import {React,useState} from 'react';

import { FormInstance } from 'antd/lib/form';
import { Modal, Form, Input, Button, message } from 'antd';

import TypeSelect from './TypeSelect';

import { TableListItem } from '../data.d';

interface UpdateFormPorps {
  visible: boolean;
  // values: Partial<TableListItem>;
  values: any;
  onSubmitLoading: boolean;
  onSubmit: (values: TableListItem, form: FormInstance) => void;
  onCancel: () => void;
}

const UpdateForm: React.FC<UpdateFormPorps> = (props) => {
  const { visible, values, onSubmit, onSubmitLoading, onCancel } = props;

  // const formVals: TableListItem = {
  //   id: values.id || 0,
  //   name: values.name || '',
  //   desc: values.desc || '',
  //   href: values.href || '',
  //   type: values.type || '',
  //   jobTitle: values.jobTitle || '',
  //   tags: values.tags || '',
  // };

  const formVals=values

  // console.log(values);

  const [tags, setTags] = useState(['tag1', 'tag2', 'tag3']);

  const handleTagChange = (index: any, value: any) => {
    // const updatedTags = [...tags];
    // updatedTags[index] = value;
    // setTags(updatedTags);
    console.log("index");
    console.log(index);
    console.log("value");
    console.log(value);
  };
  const [form] = Form.useForm();

  const onFinish = async () => {
    try {
      const fieldsValue = await form.validateFields();
      console.log("formVals");
      console.log(formVals);
      console.log("fieldsValue");
      console.log(fieldsValue);
      // values
      let vals = { ...values, ...fieldsValue }

      // let vals = { ...formVals, ...fieldsValue }
      console.log("vals");
      console.log(vals);
      onSubmit(vals, form);

      // onSubmit({ ...formVals, ...fieldsValue }, form);
    } catch (error) {
      message.warning('验证错误');
    }
  };

  return (
    <Modal
      destroyOnClose
      maskClosable={false}
      title='编辑'
      visible={visible}
      onCancel={onCancel}
      footer={[
        <Button key='back' onClick={() => onCancel()}>
          取消
        </Button>,
        <Button key='submit' type='primary' htmlType='submit' loading={onSubmitLoading} onClick={() => onFinish()}>
          提交
        </Button>,
      ]}
    >
      <Form
        form={form}
        name='createform'
        labelCol={{ span: 4 }}
        // initialValues={{
        //   ...formVals,
        //   // name: formVals.name,
        //   // href: formVals.href,
        //   // desc: formVals.desc,
        //   // type: formVals.type,
        //   // jobTitle	: formVals.jobTitle	,
        //   // tags
        // }}

        initialValues={formVals}
      >
        {/* <Form.Item
          label='位置'
          name='type'
          rules={[
            {
              required: true,
              message: '请选择',
            },
          ]}
        >
          <TypeSelect placeholder='请选择' />
        </Form.Item> */}
        {/* <Form.Item
          label='名称'
          name='name'
          rules={[
            {
              required: true,
              validator: async (rule, value) => {
                if (value === '' || !value) {
                  throw new Error('请输入名称');
                } else if (value.length > 15) {
                  throw new Error('长度不能大于15个字');
                }
              },
            },
          ]}
        >
          <Input placeholder='请输入名称' />
        </Form.Item> */}
        {/* <Form.Item
          label='网址'
          name='href'
          rules={[
            {
              required: true,
              validator: async (rule, value) => {
                if (value === '' || !value) {
                  throw new Error('请输入网址');
                } else if (
                  // eslint-disable-next-line no-useless-escape
                  !/(http|https):\/\/[\w\-_]+(\.[\w\-_]+)+([\w\-\.,@?^=%&:/~\+#]*[\w\-\@?^=%&/~\+#])?/.test(value)
                ) {
                  throw new Error('请输入正确的网址');
                }
              },
            },
          ]}
        >
          <Input placeholder='请输入网址' />
        </Form.Item> */}

        {/* <Form.Item label='备注' name='desc'>
          <Input placeholder='请输入备注' />
        </Form.Item> */}
        <Form.Item label='jobTitle' name='jobTitle'>
          <Input placeholder='请输入jobTitle' />
        </Form.Item>
        {/* <Form.Item label='jobTitle' name='jobTitle'>
          <Input placeholder='请输入jobTitle' />
        </Form.Item> */}
        {/* <Form.Item label='tags' name='tags'>
          <Input placeholder='请输入tags' />
        </Form.Item> */}

      {/* <Form.Item label='update_time' name='update_time'>
          <Input placeholder='请输入 update_time' />
        </Form.Item> */}

        <Form.Item label="update_time" name="update_time">
        {/* <div>{form.getFieldValue('update_time')}</div> */}
        {/* <div>{formVals.update_time}</div> */}
        <div>{formVals.update_time}</div> 
      </Form.Item>
      
        {/* <div></div></div> */}
        {/* form.tags.ea  */}

tags
        {formVals.tags.map((tag, index) => (
        <div key={index}>
          <Form.Item label="Word" name={['tags', index, 'word']}>
            <Input placeholder="请输入Word" />
          </Form.Item>
          <Form.Item label="Flag" name={['tags', index, 'flag']}>
            <Input placeholder="请输入Flag" />
          </Form.Item>
        </div>
      ))}

{/* update_time */}


        {/* form.each */}
      </Form>
    </Modal>
  );
};

export default UpdateForm;
