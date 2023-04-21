@PostMapping(value = "/saveMapBatch")
public Object saveMapBatch(@RequestBody List<#mapTablePascal#> #mapTableCamelCase#s) {

        try {
        #mapTablePascal# #mapTableCamelCase# = #mapTableCamelCase#s.get(0);
        Integer #fatherTableCamelCase#Id = #mapTableCamelCase#.get#fatherTablePascal#Id();
//        父亲id

        List<#mapTablePascal#> list = #mapTableCamelCase#Service.lambdaQuery().
        eq(#mapTablePascal#::get#fatherTablePascal#Id, #fatherTableCamelCase#Id).list();
//        父亲id的 map
        List<Integer> mapIds = list.stream().map(#mapTablePascal#::getId).collect(Collectors.toList());
//        他们的id

//        孩子 新传入的
//        List<Integer> #childTableCamelCase#IdsNew = #mapTableCamelCase#s.stream().
//               map(#mapTablePascal#::get#childTablePascal#Id).collect(Collectors.toList());
////        数据库里查出来的孩子
//        List<Integer> #childTableCamelCase#IdsOld =
//          list.stream().map(#mapTablePascal#::get#childTablePascal#Id).collect(Collectors.toList());
//
////        数据库的孩子记录
//        List<#childTablePascal#> #childTableCamelCase#sOld = #childTablePascal#Service.listByIds(#childTableCamelCase#IdsOld);
////        数据库的新的孩子的记录
//        List<#childTablePascal#> #childTableCamelCase#sNew = #childTablePascal#Service.listByIds(#childTableCamelCase#IdsNew);

//        删掉之前的map
        boolean rmOk = #mapTableCamelCase#Service.removeByIds(mapIds);
        boolean ok = #mapTableCamelCase#Service.saveBatch(#mapTableCamelCase#s);
        return Result.success(ok);

        } catch (Exception e) {
        e.printStackTrace();
        return ReturnT.error(e.getMessage());
        }

        }
