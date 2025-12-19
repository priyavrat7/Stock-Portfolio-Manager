/ Simulate ticks and publish directly to RDB on 5011 (dev mode)
syms:`AAPL`MSFT`GOOGL`AMZN`META`NVDA
px:100.0 300.0 140.0 3200.0 320.0 450.0

h:hopen (`::;5012)
send:{[t;d] h(".u.upd"; t; d)}

show "Starting simulated feed";
while[1b;
  px+:0.01 * 1 -1?count syms;
  d:([] sym:syms; px:px);
  send[`quote; d];
  system "t 1000";   / every 1s
]