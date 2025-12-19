/ Real-time dev RDB on port 5012
system "p 5012"

/ schema (value-only quote table)
quote:([] sym:`symbol$(); px:`float$())

/ built-in Alpha Vantage fetch (1s) — real prices
syms:`AAPL`MSFT`GOOGL`AMZN`META`NVDA
key:"YOUR_ALPHA_VANTAGE_KEY"   / TODO: replace with your real key
idx:-1                          / rotation index for symbols

get1:{[s;k]
  url:"https://www.alphavantage.co/query?function=GLOBAL_QUOTE&symbol=",string s,"&apikey=",k;
  j:.j.k .Q.hg url;
  g:j[`Global\ Quote];
  px: real g[`05.\ price];
  ([] sym:enlist s; px:enlist px)
}

system "t 12000"               / 12s per API call to respect 5/min free limit
.z.ts:{
  if[not count key; :()];
  idx: (idx + 1) mod count syms;
  quote upsert get1[syms idx; key]
}

/ Minimal RDB (no subscriptions; Java polls)
.u.upd:{[t;d] if[t=`quote; quote,:d]; ::}