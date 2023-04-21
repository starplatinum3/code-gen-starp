toggleHave() {
    let data = #jsonDefaultNull#

    axios.post(Common.baseUrl + `/#mapTableName#/selectPage/equal?pageNumber=${0}&pageSize=${9999}`, data)
        .then(res => {

    let #mapTableName#s = res.data.data.records;

    this.#mapTableName#sDbHave=[]

#mapTableName#s.forEach(#mapTableName# => {

        let  idx= ListUtil.indexOfValWithProp(this.tableData,#mapTableName#.#compareProp#,"#compareProp#")
        if(idx!==ListUtil.notFound){
        let row=this.tableData[idx]
        this.#mapTableName#DbHave.push(#mapTableName#)
        this.$refs.multipleTable.toggleRowSelection(row, true)

    }


})


});
},
