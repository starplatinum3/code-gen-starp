export type #className#Type = {
  #tsDataDFields#
};


export type #className#ListPagination = {
  total: number;
  pageSize: number;
  current: number;
};

export type #className#ListData = {
  list: #className#Type[];
  pagination: Partial<#className#ListPagination>;
};

export type #className#ListParams = {
  #tsDataDListParamsFields#

};
